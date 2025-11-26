package com.challenge.coupon.unit;

import com.challenge.coupon.controller.CouponCodeFormatter;
import com.challenge.coupon.exception.InvalidCouponCodeException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CouponCodeFormatterTest {


    private final CouponCodeFormatter formatter = new CouponCodeFormatter();

    @Test
    void shouldFormatValidCode() {
        String result = formatter.format("ABCDEF");
        assertEquals("ABCDEF", result);
    }

    @Test
    void shouldCleanSpecialCharacters() {
        String result = formatter.format("A@B#C$D%E*F");
        assertEquals("ABCDEF", result);
    }

    @Test
    void shouldThrowExceptionWhenLengthIsInvalid() {
        assertThrows(InvalidCouponCodeException.class, () -> formatter.format("ABC"));
    }
    
    @Test
    void shouldThrowExceptionWhenNull() {
        assertThrows(InvalidCouponCodeException.class, () -> formatter.format(null));
    }
}