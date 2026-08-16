package com.cafe.payment_service.controller;

import com.cafe.payment_service.dto.PaymentDto;
import com.cafe.payment_service.entity.Payment;
import com.cafe.payment_service.service.PaymentService;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    // ===== Make Payment =====
    @PostMapping
    public Map<String, String> makePayment(@RequestBody PaymentDto request) {

        Payment payment = paymentService.makePayment(request);

        Map<String, String> response = new HashMap<>();

        response.put("message", "Payment Successful");
        response.put("status", payment.getStatus());

        return response;
    }

    // ===== Payment History =====
    @GetMapping
    public List<Payment> getAllPayments() {
        return paymentService.getAllPayments();
    }

    // ===== Payment Details =====
    @GetMapping("/{id}")
    public Payment getPaymentById(@PathVariable Long id) {
        return paymentService.getPaymentById(id);
    }
} 