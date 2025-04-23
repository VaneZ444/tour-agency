package xyz.vanez.orchestrator.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;


@Configuration
@EnableRabbit
public class RabbitMQConfig {

    @Bean
    public DirectExchange orchestratorExchange() {
        return new DirectExchange("orchestrator.exchange");
    }

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

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}