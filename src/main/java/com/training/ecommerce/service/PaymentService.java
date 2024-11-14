package com.training.ecommerce.service;

import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    public String processPayment(Long userId, double amount){

        if (Math.random() > 0.1){//simulate a 90% rate
            System.out.println("payment processed for user ID: " + userId + " with amount: $ " + amount);
            return "Succes";
             } else {
            System.out.println("Payment failed  for user id : " + userId + " with amount : $ " + amount);
            return "Failure";
        }
    }
    public String refundPayment(Long userId, double amount){
        if (Math.random() > 0.1){
            System.out.println("refund processed for user id:" + userId + " with amount" + amount);
            return "Succes";
        }else {
            System.out.println("refund failed for user id:" + userId + " with amount " + amount);
            return "Failure";
        }
    }
}
