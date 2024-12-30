package ie.atu.delivery_order_service;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "orders" )
@Data
public class Order {
    @Id
    private String orderId;
    private String customerId;
    private String restaurantId;
    private List<String> items;
    private double totalAmount;
    private String deliveryAddress;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
