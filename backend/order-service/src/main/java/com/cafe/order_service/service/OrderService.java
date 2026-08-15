package com.cafe.order_service.service;

import com.cafe.order_service.entity.Order;
import com.cafe.order_service.repository.OrderRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    // ===== Place Order =====
    public Order placeOrder(Order order) {

        order.setStatus("PENDING");

        return orderRepository.save(order);
    }

    // ===== View All Orders (Order History) =====
    @SuppressWarnings("unchecked")
    public List<Order> getAllOrders() {

        return (List<Order>) (List<?>) orderRepository.findAll();
    }

    // ===== View Single Order =====
    public Order getOrderById(Long id) {

        return (Order) orderRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Order not found with id: " + id)
                );
    }

    // ===== Update Order =====
    public Order updateOrder(Long id, Order updatedOrder) {

        Order existingOrder = getOrderById(id);

        existingOrder.setCustomerName(updatedOrder.getCustomerName());
        existingOrder.setFoodId(updatedOrder.getFoodId());
        existingOrder.setQuantity(updatedOrder.getQuantity());
        existingOrder.setTotal(updatedOrder.getTotal());
        existingOrder.setStatus(updatedOrder.getStatus());

        return orderRepository.save(existingOrder);
    }

    // ===== Cancel/Delete Order =====
    public void deleteOrder(Long id) {

        Order existingOrder = getOrderById(id);

        orderRepository.delete(existingOrder);
    }
}