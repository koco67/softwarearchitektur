package com.htw.checkout.configuration;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.ConditionalRejectingErrorHandler;
import org.springframework.amqp.rabbit.listener.FatalExceptionStrategy;
import org.springframework.util.ErrorHandler;
import org.springframework.web.ErrorResponseException;

import com.htw.checkout.listener.Listener;
import com.htw.checkout.service.impl.CheckoutServiceImpl;


@Configuration
public class RabbitConfiguration {

    @Value("${xchange.name}")
    private String directXchangeName;

    @Value("${routing-keys.checkout-service}")
    private String checkoutServiceRoutingKey;
    
    @Value("${queue-names.checkoutservice}")
    private String checkoutServiceQueueName;
    
    @Bean
    public Listener rabbitController() {
        return new Listener();
    }
    
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(directXchangeName);
    }
    

    @Bean
    public CheckoutServiceImpl checkoutService() {
        return new CheckoutServiceImpl();
    }

    @Bean
    public Queue checkoutServiceQueue() {
        return new Queue(checkoutServiceQueueName);
    }

    @Bean
    public Binding checkoutServiceBinding(DirectExchange directExchange, Queue checkoutServiceQueue) {
        return BindingBuilder.bind(checkoutServiceQueue).to(directExchange).with(checkoutServiceRoutingKey);
    }

    @Bean
    public ErrorHandler errorHandler() {
        return new ConditionalRejectingErrorHandler(customExceptionStrategy());
    }

    @Bean
    FatalExceptionStrategy customExceptionStrategy() {
        return new MyFatalExceptionStrategy();
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
