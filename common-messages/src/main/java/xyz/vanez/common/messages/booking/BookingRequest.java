package xyz.vanez.common.messages.booking;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;
@Getter
@AllArgsConstructor
@Data
public class BookingRequest {
    private String bookingId;
    private String clientId;
    private String tourId;
    private LocalDate bookingDate;
}
