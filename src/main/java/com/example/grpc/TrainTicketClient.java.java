package com.example.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class TrainTicketClient {

    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090)
                .usePlaintext()
                .build();

        TrainTicketServiceGrpc.TrainTicketServiceBlockingStub blockingStub = TrainTicketServiceGrpc.newBlockingStub(channel);

        // Use blockingStub to call gRPC methods and display results in console

        channel.shutdown();
    }
}
