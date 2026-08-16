package com.cafe.payment_service.service;

import com.cafe.payment_service.dto.PaymentDto;
import com.cafe.payment_service.entity.Payment;
import com.cafe.payment_service.repository.PaymentRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    // ===== Make Payment (Mock) =====
    public Payment makePayment(PaymentDto request) {

        // ===== Basic Validation =====
        if (request.getOrderId() == null ||
                request.getAmount() == null ||
                request.getAmount() <= 0) {

            throw new RuntimeException("Invalid payment request");
        }

        // ===== Mock Payment Logic =====
        Payment payment = new Payment();

        payment.setOrderId(request.getOrderId());
        payment.setAmount(request.getAmount());
        payment.setMethod(request.getMethod());
        payment.setStatus("SUCCESS");   // always success (mock)

        return paymentRepository.save(payment);
    }

    // ===== Payment History (All Payments) =====
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    // ===== Payment Details (Single) =====
    public Payment getPaymentById(Long id) {

        return paymentRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Payment not found with id: " + id)
                );
    }
}