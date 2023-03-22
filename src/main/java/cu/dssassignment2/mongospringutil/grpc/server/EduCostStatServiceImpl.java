package cu.dssassignment2.mongospringutil.grpc.server;

import cu.assignment2.proto.*;
import cu.dssassignment2.mongospringutil.model.EduCostStat;
import cu.dssassignment2.mongospringutil.model.EduCostStatQueryOne;
import cu.dssassignment2.mongospringutil.repository.*;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class EduCostStatServiceImpl extends EduCostStatServiceGrpc.EduCostStatServiceImplBase {
    private final EduCostStatRepository eduCostStatRepository;
    @Autowired
    EduCostStatQueryOneRepository eduCostStatQueryOneRepository;
    @Autowired
    EduCostStatQueryTwoRepository eduCostStatQueryTwoRepository;
    @Autowired
    EduCostStatQueryThreeRepository eduCostStatQueryThreeRepository;
    @Autowired
    EduCostStatQueryFourRepository eduCostStatQueryFourRepository;
    @Autowired
    EduCostStatQueryFiveRepository eduCostStatQueryFiveRepository;

    @Autowired
    public EduCostStatServiceImpl(EduCostStatRepository eduCostStatRepository) {
        this.eduCostStatRepository = eduCostStatRepository;
    }

    @Override
    public void test(Request request, StreamObserver<Response> responseObserver) {
        System.out.println("Received request with name: " + request.getName());
        Response response = Response.newBuilder().setName("Hello, " + request.getName()).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void q1(QueryRequest request, StreamObserver<QueryResponse> responseObserver) {
        List<EduCostStat> results = eduCostStatRepository.findByYearAndStateAndTypeAndLengthAndExpense(request.getYear(), request.getState(),
                request.getType(), request.getLength(), request.getExpense());

        List<EduCostStatQueryOne> q1Results = new ArrayList<>();
        Set<String> uniqueRecords = new HashSet<>();
        for (EduCostStat eduCostStat : results) {
            String key = String.format("%s-%s-%s-%s-%s-%s", eduCostStat.getYear(), eduCostStat.getState(),
                    eduCostStat.getType(), eduCostStat.getLength(), eduCostStat.getExpense(), eduCostStat.getValue());

            if (!uniqueRecords.contains(key)) {
                EduCostStatQueryOne queryOne = new EduCostStatQueryOne();
                queryOne.setId(eduCostStat.getId());
                queryOne.setYear(eduCostStat.getYear());
                queryOne.setState(eduCostStat.getState());
                queryOne.setType(eduCostStat.getType());
                queryOne.setLength(eduCostStat.getLength());
                queryOne.setExpense(eduCostStat.getExpense());
                queryOne.setValue(eduCostStat.getValue());
                q1Results.add(queryOne);
                uniqueRecords.add(key);
            }
        }
        eduCostStatQueryOneRepository.saveAll(q1Results);
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