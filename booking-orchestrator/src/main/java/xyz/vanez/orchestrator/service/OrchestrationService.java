package xyz.vanez.orchestrator.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import xyz.vanez.common.messages.booking.BookingCreatedEvent;
import xyz.vanez.common.messages.booking.BookingRequest;
import xyz.vanez.common.messages.client.ClientVerificationRequest;
import xyz.vanez.common.messages.client.ClientVerificationResponse;
import xyz.vanez.common.messages.payment.PaymentProcessedEvent;
import xyz.vanez.common.messages.payment.PaymentRequest;
import xyz.vanez.orchestrator.state.BookingEvent;
import xyz.vanez.orchestrator.state.BookingStateMachine;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OrchestrationService {

    private final RabbitTemplate rabbitTemplate;
    private final BookingStateMachine stateMachine;

    public OrchestrationService(RabbitTemplate rabbitTemplate, BookingStateMachine stateMachine) {
        this.rabbitTemplate = rabbitTemplate;
        this.stateMachine = stateMachine;
    }

    public void startBookingProcess(BookingRequest request) {
        log.info("Starting booking process for booking: {}, client: {}", request.getBookingId(), request.getClientId());
        stateMachine.startProcess(request.getBookingId());

        // Отправляем запрос на проверку клиента
        rabbitTemplate.convertAndSend(
                "services.exchange",
                "client.verify",
                new ClientVerificationRequest(request.getClientId(), request.getBookingId())
        );
    }

    public void processClientVerified(ClientVerificationResponse response) {
        log.info("Processing client verification response for booking: {}", response.getRequestId());
        stateMachine.sendEvent(BookingEvent.CLIENT_VERIFIED);

        // Исправленный запрос на бронирование
        rabbitTemplate.convertAndSend(
                "services.exchange",
                "booking.create",
                new BookingRequest(
                        response.getRequestId(),   // BOOK-101
                        response.getClientId(),    // CLT-123
                        "TOUR-123",
                        null
                )
        );
        log.info("Sent booking creation request for booking: {}", response.getRequestId());
    }

    public void handleVerificationFailed(ClientVerificationResponse response) {
        log.error("Client verification failed for booking: {}", response.getClientId());
        stateMachine.sendEvent(BookingEvent.CLIENT_VERIFICATION_FAILED);
        // Логика обработки ошибки
    }

    public void processBookingCreated(BookingCreatedEvent event) {
        log.info("Processing booking created event for booking: {}", event.getBookingId());
        stateMachine.sendEvent(BookingEvent.BOOKING_CREATED);

        // Логика обработки платежа
        rabbitTemplate.convertAndSend(
                "services.exchange",
                "payment.process",
                new PaymentRequest(event.getBookingId(), "CARD-123")
        );
    }

    public void handleBookingCreationFailed(BookingCreatedEvent event) {
        log.error("Booking creation failed for booking: {}", event.getBookingId());
        stateMachine.sendEvent(BookingEvent.BOOKING_CREATION_FAILED);
        // Логика обработки ошибки
    }

    public void processPaymentProcessed(PaymentProcessedEvent event) {
        log.info("Processing payment processed event for booking: {}", event.getBookingId());
        stateMachine.sendEvent(BookingEvent.PAYMENT_PROCESSED);
        // Логика завершения процесса
    }

    public void handlePaymentProcessingFailed(PaymentProcessedEvent event) {
        log.error("Payment processing failed for booking: {}", event.getBookingId());
        stateMachine.sendEvent(BookingEvent.PAYMENT_PROCESSING_FAILED);
        // Логика обработки ошибки
    }
}
