package xyz.vanez.common.messages.booking;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Data
public class BookingCreatedEvent {
    private String bookingId;
    private boolean isSuccess;
}
