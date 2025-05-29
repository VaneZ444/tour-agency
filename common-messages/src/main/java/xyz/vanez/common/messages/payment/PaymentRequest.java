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
public class PaymentRequest {
    @JsonProperty("bookingId")
    String bookingId;
    @JsonProperty("cardId")
    String cardId;
}
