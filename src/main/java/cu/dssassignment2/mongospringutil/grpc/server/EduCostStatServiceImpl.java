package cu.dssassignment2.mongospringutil.grpc.server;

import cu.assignment2.proto.*;
import cu.dssassignment2.mongospringutil.model.EduCostStat;
import cu.dssassignment2.mongospringutil.repository.EduCostStatRepository;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EduCostStatServiceImpl extends EduCostStatServiceGrpc.EduCostStatServiceImplBase {

    @Autowired
    EduCostStatRepository eduCostStatRepository;

    @Override
    public void test(Request request, StreamObserver<Resp> responseObserver) {
        System.out.println("Received request with name: " + request.getName());
        Resp response = Resp.newBuilder().setName("Hello, " + request.getName()).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }


    @Override
    public void eduCostStatQueryOne(RequestYear request, StreamObserver<EduCostStatResponse> responseObserver) {
        List<EduCostStat> list= eduCostStatRepository.findAll();

        List<EduCostStatFields> listStat= (List<EduCostStatFields>) list.stream().map((item) ->{
                    EduCostStatFields stat = EduCostStatFields.newBuilder().setId(item.getId()).setExpense(item.getExpense())
                            .setLength(item.getLength()).setType(item.getType()).setState(item.getState()).setValue(item.getValue())
                            .setYear(item.getYear()).build();
                    return stat;
                }).collect(Collectors.toList());;

        EduCostStatResponse allStats=EduCostStatResponse.newBuilder().addAllStatFields(listStat).build();
        responseObserver.onNext(allStats);
        responseObserver.onCompleted();
    }

}

