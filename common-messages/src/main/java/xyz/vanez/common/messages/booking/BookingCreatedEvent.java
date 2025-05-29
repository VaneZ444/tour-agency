package xyz.vanez.common.messages.booking;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@Data
@NoArgsConstructor
public class BookingCreatedEvent {
    @JsonProperty("bookingId")
    private String bookingId;
    @JsonProperty("isSuccess")
    private boolean isSuccess;
}
