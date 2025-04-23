package xyz.vanez.payment.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import xyz.vanez.common.messages.payment.PaymentRequest;
import xyz.vanez.payment.service.PaymentService;

@Component
public class PaymentListener {

    private final PaymentService paymentService;

    public PaymentListener(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @RabbitListener(queues = "payment.process.queue")
    public void handlePaymentRequest(PaymentRequest request) {
        paymentService.processPayment(request);
    }
}
