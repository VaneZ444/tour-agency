package xyz.vanez.common.messages.payment;

public record PaymentRequest(
        String bookingId,
        String cardId
) {}
