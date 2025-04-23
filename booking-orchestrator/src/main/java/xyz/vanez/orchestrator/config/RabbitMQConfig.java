package xyz.vanez.orchestrator.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitMQConfig {

    // Exchange для общения с сервисами
    @Bean
    public DirectExchange servicesExchange() {
        return new DirectExchange("services.exchange");
    }

    // Exchange для ответов
    @Bean
    public DirectExchange orchestratorExchange() {
        return new DirectExchange("orchestrator.exchange");
    }

    // Очереди для слушания ответов
    @Bean
    public Queue clientVerificationQueue() {
        return new Queue("orchestrator.client.verified.queue", true);
    }

    @Bean
    public Queue bookingCreatedQueue() {
        return new Queue("orchestrator.booking.created.queue", true);
    }

    @Bean
    public Queue paymentProcessedQueue() {
        return new Queue("orchestrator.payment.processed.queue", true);
    }

    // Привязки
    @Bean
    public Binding clientVerifiedBinding() {
        return BindingBuilder.bind(clientVerificationQueue())
                .to(orchestratorExchange())
                .with("client.verified");
    }

    @Bean
    public Binding bookingCreatedBinding() {
        return BindingBuilder.bind(bookingCreatedQueue())
                .to(orchestratorExchange())
                .with("booking.created");
    }

    @Bean
    public Binding paymentProcessedBinding() {
        return BindingBuilder.bind(paymentProcessedQueue())
                .to(orchestratorExchange())
                .with("payment.processed");
    }
}
