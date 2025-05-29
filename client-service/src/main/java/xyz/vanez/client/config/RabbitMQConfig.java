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

    @Bean
    public DirectExchange servicesExchange() {
        return new DirectExchange("services.exchange");
    }

    @Bean
    public Queue clientVerifyQueue() {
        return new Queue("client.verify.queue", true);
    }

    @Bean
    public Binding clientVerifyBinding() {
        return BindingBuilder.bind(clientVerifyQueue())
                .to(servicesExchange())
                .with("client.verify");
    }

    @Bean
    public Queue clientVerificationResponseQueue() {
        return new Queue("client.verification.response.queue", true);
    }

    @Bean
    public Binding clientVerificationResponseBinding() {
        return BindingBuilder.bind(clientVerificationResponseQueue())
                .to(servicesExchange())
                .with("client.verified");
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
