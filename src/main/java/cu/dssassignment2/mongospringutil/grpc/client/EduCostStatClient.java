package cu.dssassignment2.mongospringutil.grpc.client;

import cu.assignment2.proto.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class EduCostStatClient {
   static ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50057)
            .usePlaintext()
            .build();
    static EduCostStatServiceGrpc.EduCostStatServiceBlockingStub stub = EduCostStatServiceGrpc.newBlockingStub(channel);

    public static void main(String[] args) {

        if (args.length == 0) {
            System.out.println("Need one argument to work");
            return;
        }

        switch (args[0]) {
//            case "test":
//            doTest(channel);
//            break;
            case "q1":
                doQuerying1(channel);
                break;

            default:
                System.out.println("Keyword Invalid: " + args[0]);
        }

        System.out.println("Shutting down");
        channel.shutdown();
    }

    public static List<EduCostStat> doQuerying1(ManagedChannel channel) {
        RequestYear request = RequestYear.newBuilder().setYear("2013").build();
//        EduCostStatResponse response= stub.eduCostStatQueryOne(request);
//        System.out.println(response);
        return new ArrayList<>();
    }
//
//    private static void doTest(ManagedChannel channel) {
//        Resp response = stub.test(Request.newBuilder().setName("Abishek").build());
//        System.out.println(response.getName());
//    }


}
