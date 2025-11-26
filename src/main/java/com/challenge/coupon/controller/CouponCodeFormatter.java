package com.challenge.coupon.controller; // ou o pacote correto

import com.challenge.coupon.exception.InvalidCouponCodeException;
import org.springframework.stereotype.Component;

@Component
public class CouponCodeFormatter {

    public String format(String rawCode) {
        if (rawCode == null) {
            throw new InvalidCouponCodeException("O código do cupom não pode ser nulo.");
        }

        String formatted = rawCode.replaceAll("[^a-zA-Z0-9]", "");
        if (formatted.length() != 6) {
            throw new InvalidCouponCodeException("O código deve ter exatamente 6 caracteres alfanuméricos após a formatação.");
        }

        return formatted.toUpperCase();
    }
}