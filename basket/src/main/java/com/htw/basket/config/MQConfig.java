package com.htw.basket.config;

import com.htw.basket.service.impl.BasketService;
import com.htw.basket.listener.Listener;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.ConditionalRejectingErrorHandler;
import org.springframework.amqp.rabbit.listener.FatalExceptionStrategy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ErrorHandler;
import org.springframework.web.ErrorResponseException;

@Configuration
public class MQConfig {

    @Value("${xchange.name}")
    private String directXchangeName;

    @Value("${routing-keys.basket-service}")
    private String basketServiceRoutingKey;

    @Value("${queue-names.basket-service}")
    private String basketServiceQueueName;

    @Bean
    public Listener rabbitController() {
        return new Listener();
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(directXchangeName);
    }


    @Bean
    public BasketService basketService() {
        return new BasketService();
    }

    @Bean
    public Queue basketServiceQueue() {
        return new Queue(basketServiceQueueName);
    }

    @Bean
    public Binding basketServiceBinding(DirectExchange directExchange, Queue basketServiceQueue) {
        return BindingBuilder.bind(basketServiceQueue).to(directExchange).with(basketServiceRoutingKey);
    }

    @Bean
    public ErrorHandler errorHandler() {
        return new ConditionalRejectingErrorHandler(customExceptionStrategy());
    }

    @Bean
    FatalExceptionStrategy customExceptionStrategy() {
        return new com.htw.basket.config.MQConfig.MyFatalExceptionStrategy();
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory,
                                                                               SimpleRabbitListenerContainerFactoryConfigurer configurer) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        factory.setErrorHandler(errorHandler());
        return factory;
    }

    private static class MyFatalExceptionStrategy extends ConditionalRejectingErrorHandler.DefaultExceptionStrategy {
        @Override
        public boolean isFatal(Throwable t) {
            return !(t.getCause() instanceof ErrorResponseException);
        }
    }
}
