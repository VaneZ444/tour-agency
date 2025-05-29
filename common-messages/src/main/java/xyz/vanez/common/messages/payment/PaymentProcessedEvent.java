package xyz.vanez.common.messages.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentProcessedEvent {
    @JsonProperty("bookingId")
    private String bookingId;
    @JsonProperty("isSuccess")
    private boolean isSuccess;
}
