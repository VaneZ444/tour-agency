package xyz.vanez.common.messages.payment;

public record PaymentProcessedEvent(
        String bookingId,
        boolean isSuccess
) {}
