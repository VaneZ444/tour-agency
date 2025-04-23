package xyz.vanez.common.messages.booking;

public record BookingCreatedEvent(
        String bookingId,
        boolean isSuccess
) {}