package xyz.vanez.payment.service;

import xyz.vanez.common.messages.payment.PaymentRequest;

public interface PaymentService {
    void processPayment(PaymentRequest request);
}
