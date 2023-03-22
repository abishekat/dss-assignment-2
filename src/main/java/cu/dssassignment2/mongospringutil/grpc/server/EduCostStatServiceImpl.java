package cu.dssassignment2.mongospringutil.grpc.server;

import cu.assignment2.proto.*;
import cu.dssassignment2.mongospringutil.model.EduCostStat;
import cu.dssassignment2.mongospringutil.repository.EduCostStatRepository;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EduCostStatServiceImpl extends EduCostStatServiceGrpc.EduCostStatServiceImplBase {
    private final EduCostStatRepository eduCostStatRepository;
   @Autowired
   public EduCostStatServiceImpl(EduCostStatRepository eduCostStatRepository) {
       this.eduCostStatRepository = eduCostStatRepository;
   }


    @Override
    public void test(Request request, StreamObserver<Resp> responseObserver) {
        System.out.println("Received request with name: " + request.getName());
        Resp response = Resp.newBuilder().setName("Hello, " + request.getName()).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void queryEduCostStat(QueryRequest request, StreamObserver<QueryResponse> responseObserver) {
//        List<EduCostStat> results = eduCostStatRepository.findByYearAndStateAndTypeAndLengthAndExpense(request.getYear(), request.getState(),
//                request.getType(), request.getLength(), request.getExpense());
        System.out.println("Received request with name: " + request.getYear());
        List<EduCostStat> results = eduCostStatRepository.findAll();

        QueryResponse.Builder responseBuilder = QueryResponse.newBuilder();
        for (EduCostStat eduCostStat : results) {
            cu.assignment2.proto.EduCostStat.Builder eduCostStatBuilder = cu.assignment2.proto.EduCostStat.newBuilder();
            eduCostStatBuilder.setId(eduCostStat.getId());
            eduCostStatBuilder.setYear(eduCostStat.getYear());
            eduCostStatBuilder.setState(eduCostStat.getState());
            eduCostStatBuilder.setType(eduCostStat.getType());
            eduCostStatBuilder.setLength(eduCostStat.getLength());
            eduCostStatBuilder.setExpense(eduCostStat.getExpense());
            eduCostStatBuilder.setValue(eduCostStat.getValue());

            responseBuilder.addEduCostStats(eduCostStatBuilder.build());
        }
        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }


}