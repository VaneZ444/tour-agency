package xyz.vanez.client.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import lombok.extern.slf4j.Slf4j;
import xyz.vanez.common.messages.client.ClientVerificationRequest;
import xyz.vanez.common.messages.client.ClientVerificationResponse;

@Slf4j
@RestController
public class ClientController {

    private final RabbitTemplate rabbitTemplate;

    public ClientController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
    /*
    @RabbitListener(queues = "client.verify.queue")
    public void verifyClient(ClientVerificationRequest request) {
        log.info("Received verification request for client: {}", request.getClientId());
        // Логика проверки клиента
        boolean isValid = true; // Заглушка
        String requestId = request.getRequestId(); // Заглушка

        ClientVerificationResponse response = new ClientVerificationResponse(requestId, isValid, request.getClientId());
        rabbitTemplate.convertAndSend("client.verification.response.queue", response);
        log.info("Sent verification response for client: {}", request.getClientId());
    }
     */
}
