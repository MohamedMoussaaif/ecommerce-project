package com.ecommerce.rabbit;

import com.ecommerce.dto.orderDTO.RequestOrder;
import com.ecommerce.dto.rabbit.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import static com.ecommerce.config.RabbitMQConfig.QUEUE_NAME;

@Service
@RequiredArgsConstructor
public class OrderProducer {

    private final RabbitTemplate rabbitTemplate;

    public void sendOrderMessage(OrderCreatedEvent order) {
        rabbitTemplate.convertAndSend(QUEUE_NAME, order);
    }
}
