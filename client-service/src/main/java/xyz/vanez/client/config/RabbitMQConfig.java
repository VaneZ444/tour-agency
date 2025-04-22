package xyz.vanez.client.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitMQConfig {

    // 1. Обменники (Exchanges)
    @Bean
    public DirectExchange clientExchange() {
        return new DirectExchange("client.exchange");
    }

    @Bean
    public DirectExchange orchestratorExchange() {
        return new DirectExchange("orchestrator.exchange");
    }

    // 2. Очереди (Queues)
    @Bean
    public Queue clientVerificationQueue() {
        return new Queue("client.verify.queue", true); // durable=true
    }

    @Bean
    public Queue clientVerifiedResponseQueue() {
        return new Queue("orchestrator.client.verified.queue", true);
    }

    // 3. Привязки (Bindings)
    @Bean
    public Binding verificationBinding() {
        return BindingBuilder.bind(clientVerificationQueue())
                .to(clientExchange())
                .with("client.verify");
    }

    @Bean
    public Binding verifiedResponseBinding() {
        return BindingBuilder.bind(clientVerifiedResponseQueue())
                .to(orchestratorExchange())
                .with("client.verified");
    }
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }
    /*
     * Схема взаимодействия:
     *
     * Booking Orchestrator --(client.verify)--> client.exchange --> client.verify.queue (Client Service)
     * Client Service --(client.verified)--> orchestrator.exchange --> orchestrator.client.verified.queue (Orchestrator)
     */
}