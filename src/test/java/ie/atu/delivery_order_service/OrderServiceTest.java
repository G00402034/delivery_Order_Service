package ie.atu.delivery_order_service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    @Mock
    private MongoTemplate mongoTemplate;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    private Order sampleOrder;

    @BeforeEach
    void setUp() {
        // Initialize Mockito
        MockitoAnnotations.openMocks(this);

        // Initialize a sample order
        sampleOrder = new Order();
        sampleOrder.setOrderId("1");
        sampleOrder.setCustomerId("customer123");
        sampleOrder.setRestaurantId("restaurant456");
        sampleOrder.setItems(Arrays.asList("item1", "item2", "item3"));
        sampleOrder.setTotalAmount(50.75);
        sampleOrder.setDeliveryAddress("123 Main St");
        sampleOrder.setStatus("Pending");
        sampleOrder.setCreatedAt(LocalDateTime.now());
        sampleOrder.setUpdatedAt(LocalDateTime.now());
    }

    @Test
    void testCreateOrder() {
        // Mock the repository to return the sample order when save is called
        when(orderRepository.save(sampleOrder)).thenReturn(sampleOrder);

        // Call the service method
        Order createdOrder = orderService.createOrder(sampleOrder);

        // Assertions
        assertNotNull(createdOrder, "The created order should not be null");
        assertEquals(sampleOrder.getCustomerId(), createdOrder.getCustomerId());
        verify(orderRepository, times(1)).save(sampleOrder);
    }

    @Test
    void testGetOrderById() {
        String orderId = "1";

        // Mock repository behavior
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(sampleOrder));

        // Call service method
        Order fetchedOrder = orderService.getOrderById(orderId);

        // Assertions
        assertNotNull(fetchedOrder, "The fetched order should not be null");
        assertEquals(orderId, fetchedOrder.getOrderId());
        verify(orderRepository, times(1)).findById(orderId);
    }


    @Test
    void testUpdateOrderStatus() {
        String orderId = "1";
        String newStatus = "Shipped";

        // Mock behavior for finding and saving the order
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(sampleOrder));
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Call the service method
        Order updatedOrder = orderService.updateOrderStatus(orderId, newStatus);

        // Verify the order status is updated
        assertNotNull(updatedOrder);
        assertEquals(newStatus, updatedOrder.getStatus());
        verify(orderRepository, times(1)).findById(orderId);
        verify(orderRepository, times(1)).save(sampleOrder);

        // Uncomment if RabbitMQ is used:
        // verify(rabbitTemplate, times(1)).convertAndSend("order-status-queue", "Order Updated: " + orderId + " - " + newStatus);
    }



}


