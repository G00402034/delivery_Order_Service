package ie.atu.delivery_order_service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderServiceIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testCreateOrder() {
        // Arrange
        Order order = new Order();
        order.setCustomerId("customer123");
        order.setRestaurantId("restaurant456");
        order.setTotalAmount(25.50);
        order.setDeliveryAddress("123 Main St");

        // Act
        ResponseEntity<Order> response = restTemplate.postForEntity("/api/orders", order, Order.class);

        // Assert
        assertEquals(201, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("customer123", response.getBody().getCustomerId());
    }
}
