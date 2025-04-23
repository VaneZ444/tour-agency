package xyz.vanez.orchestrator.state;

public enum BookingState {
    STARTED,
    CLIENT_VERIFICATION_PENDING,
    BOOKING_CREATION_PENDING,
    PAYMENT_PROCESSING_PENDING,
    COMPLETED,
    FAILED
}