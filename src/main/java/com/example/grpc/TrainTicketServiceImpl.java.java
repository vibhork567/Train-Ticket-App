package com.example.grpc;

import io.grpc.stub.StreamObserver;

import java.util.HashMap;
import java.util.Map;

public class TrainTicketServiceImpl extends TrainTicketServiceGrpc.TrainTicketServiceImplBase {

    private final Map<String, TicketReceipt> userTickets = new HashMap<>();

    @Override
    public void purchaseTicket(TicketRequest request, StreamObserver<TicketReceipt> responseObserver) {
        // Logic to assign a seat and generate a receipt
        // (In-memory storage for simplicity, real implementation should use a database)

        String section = Math.random() < 0.5 ? "A" : "B";
        String seat = "S" + (int) (Math.random() * 100);

        TicketReceipt receipt = TicketReceipt.newBuilder()
                .setFrom(request.getFrom())
                .setTo(request.getTo())
                .setUserFirstName(request.getUserFirstName())
                .setUserLastName(request.getUserLastName())
                .setUserEmail(request.getUserEmail())
                .setSection(section)
                .setSeat(seat)
                .setPricePaid(20.0f)
                .build();

        userTickets.put(request.getUserEmail(), receipt);
        responseObserver.onNext(receipt);
        responseObserver.onCompleted();
    }

    // Implement other gRPC methods (GetReceiptDetails, ViewUserSeats, RemoveUser, ModifyUserSeat) similarly
}
