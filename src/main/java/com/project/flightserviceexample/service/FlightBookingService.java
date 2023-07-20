package com.project.flightserviceexample.service;

import com.project.flightserviceexample.dto.FlightBookingAcknowledgement;
import com.project.flightserviceexample.dto.FlightBookingRequest;
import com.project.flightserviceexample.entity.PassengerInfo;
import com.project.flightserviceexample.entity.PaymentInfo;
import com.project.flightserviceexample.repo.PassengerInfoRepository;
import com.project.flightserviceexample.repo.PaymentInfoRepository;
import com.project.flightserviceexample.utils.PaymentUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class FlightBookingService {

    private PassengerInfoRepository passengerInfoRepository;
    private PaymentInfoRepository paymentInfoRepository;

    @Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public FlightBookingAcknowledgement bookFlightTicket(FlightBookingRequest request){
        FlightBookingAcknowledgement acknowledgement = null;

        PassengerInfo passengerInfo = request.getPassengerInfo();
        passengerInfo = passengerInfoRepository.save(passengerInfo);

        PaymentInfo paymentInfo = request.getPaymentInfo();

        PaymentUtils.validateCreditLimit(paymentInfo.getAccountNo(), passengerInfo.getFare());

        paymentInfo.setPassengerId((passengerInfo.getPid()));
        paymentInfo.setAmount(passengerInfo.getFare());
        paymentInfoRepository.save(paymentInfo);

        return new FlightBookingAcknowledgement("SUCCESS", passengerInfo.getFare(), UUID.randomUUID().toString().split("-")[0],passengerInfo);
    }
}
