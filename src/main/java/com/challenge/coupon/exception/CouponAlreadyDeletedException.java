package com.challenge.coupon.exception;

public class CouponAlreadyDeletedException extends RuntimeException {
    public CouponAlreadyDeletedException(Long id) {
        super("O cupom com ID " + id + " já foi excluído anteriormente.");
    }
}