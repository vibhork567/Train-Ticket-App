import com.example.grpc.*;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TrainTicketServiceImplTest {

    @Mock
    private StreamObserver<TicketReceipt> mockPurchaseTicketResponseObserver;

    @Mock
    private StreamObserver<UserSeatsList> mockViewUserSeatsResponseObserver;

    @Mock
    private StreamObserver<RemoveUserResponse> mockRemoveUserResponseObserver;

    @Mock
    private StreamObserver<TicketReceipt> mockModifyUserSeatResponseObserver;

    @Test
    public void testPurchaseTicket() {
        TrainTicketServiceImpl service = new TrainTicketServiceImpl();

        // Prepare a mock request
        TicketRequest request = TicketRequest.newBuilder()
                .setFrom("London")
                .setTo("France")
                .setUserFirstName("John")
                .setUserLastName("Doe")
                .setUserEmail("john.doe@example.com")
                .build();

        // Call the actual method
        service.purchaseTicket(request, mockPurchaseTicketResponseObserver);

        // Capture the response
        ArgumentCaptor<TicketReceipt> responseCaptor = ArgumentCaptor.forClass(TicketReceipt.class);
        Mockito.verify(mockPurchaseTicketResponseObserver).onNext(responseCaptor.capture());
        TicketReceipt response = responseCaptor.getValue();

        // Verify the response
        assertEquals("London", response.getFrom());
        assertEquals("France", response.getTo());
        assertEquals("John", response.getUserFirstName());
        assertEquals("Doe", response.getUserLastName());
        assertEquals("john.doe@example.com", response.getUserEmail());
        assertTrue(response.getPricePaid() == 20.0);
    }

    @Test
    public void testViewUserSeats() {
        TrainTicketServiceImpl service = new TrainTicketServiceImpl();

        // Prepare a mock request
        ViewUserSeatsRequest request = ViewUserSeatsRequest.newBuilder()
                .setSection("A")
                .build();

        // Call the actual method
        service.viewUserSeats(request, mockViewUserSeatsResponseObserver);

        // Capture the response
        ArgumentCaptor<UserSeatsList> responseCaptor = ArgumentCaptor.forClass(UserSeatsList.class);
        Mockito.verify(mockViewUserSeatsResponseObserver).onNext(responseCaptor.capture());
        UserSeatsList response = responseCaptor.getValue();

        // Verify the response
        assertTrue(response.getSeatsList().isEmpty()); // Assuming no seats are allocated initially
    }

    @Test
    public void testRemoveUser() {
        TrainTicketServiceImpl service = new TrainTicketServiceImpl();

        // Prepare a mock request
        RemoveUserRequest request = RemoveUserRequest.newBuilder()
                .setUserEmail("john.doe@example.com")
                .build();

        // Call the actual method
        service.removeUser(request, mockRemoveUserResponseObserver);

        // Capture the response
        ArgumentCaptor<RemoveUserResponse> responseCaptor = ArgumentCaptor.forClass(RemoveUserResponse.class);
        Mockito.verify(mockRemoveUserResponseObserver).onNext(responseCaptor.capture());
        RemoveUserResponse response = responseCaptor.getValue();

        // Verify the response
        assertTrue(response.getSuccess());
    }

    @Test
    public void testModifyUserSeat() {
        TrainTicketServiceImpl service = new TrainTicketServiceImpl();

        // Initial purchase to have a user in the system
        TicketRequest purchaseRequest = TicketRequest.newBuilder()
                .setFrom("London")
                .setTo("France")
                .setUserFirstName("Jane")
                .setUserLastName("Smith")
                .setUserEmail("jane.smith@example.com")
                .build();
        service.purchaseTicket(purchaseRequest, mockPurchaseTicketResponseObserver);

        // Prepare a mock request for modifying the user's seat
        ModifyUserSeatRequest request = ModifyUserSeatRequest.newBuilder()
                .setUserEmail("jane.smith@example.com")
                .setNewSeat("S15")
                .build();

        // Call the actual method
        service.modifyUserSeat(request, mockModifyUserSeatResponseObserver);

        // Capture the response
        ArgumentCaptor<TicketReceipt> responseCaptor = ArgumentCaptor.forClass(TicketReceipt.class);
        Mockito.verify(mockModifyUserSeatResponseObserver).onNext(responseCaptor.capture());
        TicketReceipt response = responseCaptor.getValue();

        // Verify the response
        assertEquals("S15", response.getSeat());
    }
}
