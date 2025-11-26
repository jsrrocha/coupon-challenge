package com.challenge.coupon.exception;

public class CouponNotFoundException extends RuntimeException {
    public CouponNotFoundException(Long id) {
        super("Cupom com ID " + id + " não foi encontrado.");
    }
}