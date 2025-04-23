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

@Service
public class OrchestrationService {

    private final RabbitTemplate rabbitTemplate;
    private final BookingStateMachine stateMachine;

    public OrchestrationService(RabbitTemplate rabbitTemplate, BookingStateMachine stateMachine) {
        this.rabbitTemplate = rabbitTemplate;
        this.stateMachine = stateMachine;
    }

    public void startBookingProcess(BookingRequest request) {
        stateMachine.startProcess(request.getBookingId());

        // Отправляем запрос на проверку клиента
        rabbitTemplate.convertAndSend(
                "services.exchange",
                "client.verify",
                new ClientVerificationRequest(request.getClientId(), request.getBookingId())
        );
    }

    public void processClientVerified(ClientVerificationResponse response) {
        stateMachine.sendEvent(BookingEvent.CLIENT_VERIFIED);

        // Логика создания бронирования
        rabbitTemplate.convertAndSend(
                "services.exchange",
                "booking.create",
                new BookingRequest(response.getClientId(), response.getClientId(), "TOUR-123", null)
        );
    }

    public void handleVerificationFailed(ClientVerificationResponse response) {
        stateMachine.sendEvent(BookingEvent.CLIENT_VERIFICATION_FAILED);
        // Логика обработки ошибки
    }

    public void processBookingCreated(BookingCreatedEvent event) {
        stateMachine.sendEvent(BookingEvent.BOOKING_CREATED);

        // Логика обработки платежа
        rabbitTemplate.convertAndSend(
                "services.exchange",
                "payment.process",
                new PaymentRequest(event.getBookingId(), "CARD-123")
        );
    }

    public void handleBookingCreationFailed(BookingCreatedEvent event) {
        stateMachine.sendEvent(BookingEvent.BOOKING_CREATION_FAILED);
        // Логика обработки ошибки
    }

    public void processPaymentProcessed(PaymentProcessedEvent event) {
        stateMachine.sendEvent(BookingEvent.PAYMENT_PROCESSED);
        // Логика завершения процесса
    }

    public void handlePaymentProcessingFailed(PaymentProcessedEvent event) {
        stateMachine.sendEvent(BookingEvent.PAYMENT_PROCESSING_FAILED);
        // Логика обработки ошибки
    }
}
