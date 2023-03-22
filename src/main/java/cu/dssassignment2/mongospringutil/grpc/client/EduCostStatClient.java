package cu.dssassignment2.mongospringutil.grpc.client;

import cu.assignment2.proto.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class EduCostStatClient {
    static ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 6569)
            .usePlaintext()
            .build();
    static EduCostStatServiceGrpc.EduCostStatServiceBlockingStub stub = EduCostStatServiceGrpc.newBlockingStub(channel);

    public static void main(String[] args) {

        if (args.length == 0) {
            System.out.println("Need one argument to work");
            return;
        }

        switch (args[0]) {
            case "test":
                doTest(channel);
                break;
            case "q1":
                getCost(channel);
                break;

            default:
                System.out.println("Keyword Invalid: " + args[0]);
        }

        System.out.println("Shutting down");
        channel.shutdown();
    }

    private static void getCost(ManagedChannel channel) {
        QueryRequest request = QueryRequest.newBuilder()
                .setYear("2013")
                .setState("Alabama")
                .setType("Private")
                .setLength("4-year")
                .setExpense("Fees/Tuition")
                .build();

        QueryResponse response = stub.q1(request);
        for (EduCostStat eduCostStat : response.getEduCostStatsList()) {
            System.out.println(eduCostStat.getId());
            System.out.println(eduCostStat.getYear());
            System.out.println(eduCostStat.getState());
            System.out.println(eduCostStat.getType());
            System.out.println(eduCostStat.getLength());
            System.out.println(eduCostStat.getExpense());
            System.out.println(eduCostStat.getValue());
            System.out.println("-------------");
        }
    }


    private static void doTest(ManagedChannel channel) {
        Response response = stub.test(Request.newBuilder().setName("Abishek").build());
        System.out.println(response.getName());
    }


}
