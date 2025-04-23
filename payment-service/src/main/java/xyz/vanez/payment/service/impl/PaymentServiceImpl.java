package xyz.vanez.payment.service.impl;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import xyz.vanez.common.messages.payment.PaymentProcessedEvent;
import xyz.vanez.common.messages.payment.PaymentRequest;
import xyz.vanez.payment.service.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final RabbitTemplate rabbitTemplate;

    public PaymentServiceImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void processPayment(PaymentRequest request) {
        // Логика обработки платежа
        boolean isSuccess = true; // Заглушка

        PaymentProcessedEvent event = new PaymentProcessedEvent(request.bookingId(), isSuccess);
        rabbitTemplate.convertAndSend("orchestrator.exchange", "payment.processed", event);
    }
}
