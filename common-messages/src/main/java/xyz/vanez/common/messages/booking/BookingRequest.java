package xyz.vanez.common.messages.booking;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Getter
@AllArgsConstructor
@Data
@NoArgsConstructor
public class BookingRequest {
    @JsonProperty("bookingId")
    private String bookingId;
    @JsonProperty("clientId")
    private String clientId;
    @JsonProperty("tourId")
    private String tourId;
    @JsonProperty("bookingDate")
    private LocalDate bookingDate;
}
