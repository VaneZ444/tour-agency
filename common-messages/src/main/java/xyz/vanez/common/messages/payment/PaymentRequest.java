package xyz.vanez.common.messages.payment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@AllArgsConstructor
public class PaymentRequest {
    String bookingId;
    String cardId;
}
