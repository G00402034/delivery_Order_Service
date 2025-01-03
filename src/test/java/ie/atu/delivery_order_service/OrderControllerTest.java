package ie.atu.delivery_order_service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    private Order sampleOrder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        sampleOrder = new Order();
        sampleOrder.setOrderId("1");
        sampleOrder.setCustomerId("123");
        sampleOrder.setRestaurantId("456");
        sampleOrder.setItems(Arrays.asList("item1", "item2"));
        sampleOrder.setTotalAmount(50.0);
        sampleOrder.setDeliveryAddress("123 Main Street");
        sampleOrder.setStatus("Pending");
        sampleOrder.setCreatedAt(LocalDateTime.now());
        sampleOrder.setUpdatedAt(LocalDateTime.now());
    }

    @Test
    void testCreateOrder() {
        when(orderService.createOrder(sampleOrder)).thenReturn(sampleOrder);

        Order createdOrder = orderController.createOrder(sampleOrder);

        assertNotNull(createdOrder);
        assertEquals("1", createdOrder.getOrderId());
        verify(orderService, times(1)).createOrder(sampleOrder);
    }

    @Test
    void testUpdateOrderStatus() {
        sampleOrder.setStatus("Completed");
        when(orderService.updateOrderStatus("1", "Completed")).thenReturn(sampleOrder);

        Order updatedOrder = orderController.updateOrderStatus("1", "Completed");

        assertNotNull(updatedOrder);
        assertEquals("Completed", updatedOrder.getStatus());
        verify(orderService, times(1)).updateOrderStatus("1", "Completed");
    }

    @Test
    void testGetOrderById() {
        when(orderService.getOrderById("1")).thenReturn(sampleOrder);

        Order fetchedOrder = orderController.getOrderById("1");

        assertNotNull(fetchedOrder);
        assertEquals("1", fetchedOrder.getOrderId());
        verify(orderService, times(1)).getOrderById("1");
    }

    @Test
    void testGetOrdersByCustomer() {
        List<Order> orders = Arrays.asList(sampleOrder);
        when(orderService.getOrdersByCustomer("123")).thenReturn(orders);

        List<Order> fetchedOrders = orderController.getOrdersByCustomer("123");

        assertNotNull(fetchedOrders);
        assertFalse(fetchedOrders.isEmpty());
        assertEquals(1, fetchedOrders.size());
        assertEquals("123", fetchedOrders.get(0).getCustomerId());
        verify(orderService, times(1)).getOrdersByCustomer("123");
    }
}
