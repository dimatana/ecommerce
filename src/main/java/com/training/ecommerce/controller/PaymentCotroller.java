package com.training.ecommerce.controller;

import com.training.ecommerce.service.PaymentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class PaymentCotroller {
    private final PaymentService paymentService;

    public PaymentCotroller(PaymentService paymentService) {
        this.paymentService = paymentService;
    }
    public static class PaymentRequest{
        private Long userId;
        private double amount;

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }
    }
    public static class PaymentResponse{
        private String status;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public PaymentResponse(String status) {
            this.status = status;
        }
    }
    @PostMapping("/process")
    public PaymentResponse processPayment(@RequestBody PaymentRequest paymentRequest){
        String status = paymentService.processPayment(paymentRequest.getUserId(), paymentRequest.getAmount());
        return new PaymentResponse(status);
    }
    @PostMapping("/refund")
    public PaymentResponse refundPayment(@RequestBody PaymentRequest paymentRequest){
        String status = paymentService.refundPayment(paymentRequest.getUserId(), paymentRequest.getAmount());
        return new PaymentResponse(status);
    }
}
