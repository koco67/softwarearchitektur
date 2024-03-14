package com.htw.gateway.configuration;

import com.htw.gateway.service.impl.BasketServiceImpl;
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

import com.htw.gateway.service.impl.ProductServiceImpl;

@Configuration
public class RabbitConfiguration {

    @Value("${xchange.name}")
    private String directXchangeName;

    @Value("${routing-keys.product-service}")
    private String productServiceRoutingKey;
    
    @Value("${queue-names.product-service}")
    private String productServiceQueueName;

    @Value("${routing-keys.basket-service}")
    private String basketServiceRoutingKey;

    @Value("${queue-names.basket-service}")
    private String basketServiceQueueName;
    
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(directXchangeName);
    }
  
    @Bean
    public ProductServiceImpl productService() {
        return new ProductServiceImpl();
    }

    @Bean
    public BasketServiceImpl basketService() {
        return new BasketServiceImpl();
    }

    @Bean
    public Queue productServiceQueue() {
        return new Queue(productServiceQueueName);
    }
    @Bean
    public Queue basketServiceQueue() {
        return new Queue(basketServiceQueueName);
    }

    @Bean
    public Binding productServiceBinding(DirectExchange directExchange, Queue productServiceQueue) {
        return BindingBuilder.bind(productServiceQueue).to(directExchange).with(productServiceRoutingKey);
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
