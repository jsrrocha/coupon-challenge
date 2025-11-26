package com.challenge.coupon.exception;

public class InvalidCouponCodeException extends RuntimeException {
    public InvalidCouponCodeException(String message) {
        super(message);
    }
}