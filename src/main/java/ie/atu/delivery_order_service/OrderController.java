package ie.atu.delivery_order_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // Create a new order
    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        return orderService.createOrder(order);
    }

    // Update order status
    @PutMapping("/{orderId}/status")
    public Order updateOrderStatus(@PathVariable String orderId, @RequestParam String status) {
        return orderService.updateOrderStatus(orderId, status);
    }

    // Get order by ID
    @GetMapping("/{orderId}")
    public Order getOrderById(@PathVariable String orderId) {
        return orderService.getOrderById(orderId);
    }

    // Get orders by customer
    @GetMapping("/customer/{customerId}")
    public List<Order> getOrdersByCustomer(@PathVariable String customerId) {
        return orderService.getOrdersByCustomer(customerId);
    }
}
