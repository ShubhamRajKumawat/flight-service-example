package com.project.flightserviceexample.utils;

import com.project.flightserviceexample.exception.InsufficientAmountException;

import java.util.HashMap;
import java.util.Map;

public class PaymentUtils {

    private static Map<String, Double> paymentMap = new HashMap<>();
    {
        paymentMap.put("account1", 10000.0);
        paymentMap.put("account2", 10000.0);
        paymentMap.put("account3", 10000.0);
        paymentMap.put("account4", 10000.0);
    }

    public static boolean validateCreditLimit(String accNo, double paidAmount){
        if(paidAmount > paymentMap.get(accNo)){
            throw new InsufficientAmountException("Insufficient Funds");
        }else {
            return true;
        }
    }
}
