package xyz.vanez.orchestrator.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public DirectExchange orchestratorExchange() {
        return new DirectExchange("orchestrator.exchange");
    }

    @Bean
    public Queue clientVerifiedQueue() {
        return new Queue("orchestrator.client.verified.queue");
    }

    @Bean
    public Binding clientVerifiedBinding() {
        return BindingBuilder.bind(clientVerifiedQueue())
                .to(orchestratorExchange())
                .with("client.verified");
    }
}