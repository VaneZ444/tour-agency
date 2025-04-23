package xyz.vanez.booking.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitMQConfig {

    @Bean
    public DirectExchange servicesExchange() {
        return new DirectExchange("services.exchange");
    }

    @Bean
    public Queue bookingCreateQueue() {
        return new Queue("booking.create.queue", true);
    }

    @Bean
    public Binding bookingCreateBinding() {
        return BindingBuilder.bind(bookingCreateQueue())
                .to(servicesExchange())
                .with("booking.create");
    }
}

