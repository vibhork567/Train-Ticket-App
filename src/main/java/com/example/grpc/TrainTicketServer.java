package com.example.grpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class TrainTicketServer {

    public static void main(String[] args) throws IOException, InterruptedException {
        TrainTicketServer trainTicketServer = new TrainTicketServer();
        trainTicketServer.start();
    }

    public void start() throws IOException, InterruptedException {
        int serverPort = 9090;

        Server server = ServerBuilder.forPort(serverPort)
                .addService(new TrainTicketServiceImpl())
                .build();

        server.start();
        System.out.println("Server started on port " + serverPort);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down gRPC server since JVM is shutting down.");
            server.shutdown();
        }));

        server.awaitTermination();
    }
}
