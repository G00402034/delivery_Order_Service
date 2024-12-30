package ie.atu.delivery_order_service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    // Create a new order
    public Order createOrder(Order order) {
        order.setStatus("Pending");
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());
        Order savedOrder = orderRepository.save(order);

        // Publish order created event to RabbitMQ
        rabbitTemplate.convertAndSend("order-status-queue", "Order Created: " + order.getOrderId());
        return savedOrder;
    }

    // Update order status
    public Order updateOrderStatus(String orderId, String status) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            order.setStatus(status);
            order.setUpdatedAt(LocalDateTime.now());
            Order updatedOrder = orderRepository.save(order);

            // Publish order status updated event to RabbitMQ
            rabbitTemplate.convertAndSend("order-status-queue", "Order Updated: " + order.getOrderId() + " - " + status);
            return updatedOrder;
        }
        throw new RuntimeException("Order not found");
    }

    // Get order by ID
    public Order getOrderById(String orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    // Get orders for a customer
    public List<Order> getOrdersByCustomer(String customerId) {
        return orderRepository.findByCustomerId(customerId);
    }
}

