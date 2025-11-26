package com.challenge.coupon.unit;

import com.challenge.coupon.controller.CouponCodeFormatter;
import com.challenge.coupon.dto.CouponDTO;
import com.challenge.coupon.entity.Coupon;
import com.challenge.coupon.exception.CouponAlreadyDeletedException;
import com.challenge.coupon.exception.InvalidCouponCodeException;
import com.challenge.coupon.repository.CouponRepository;
import com.challenge.coupon.service.CouponService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CouponServiceTest {

    @InjectMocks
    private CouponService service;

    @Mock
    private CouponRepository repository;

    @Mock
    private CouponCodeFormatter formatter;

    @Test
    void shouldFormatCodeAndCreateCoupon() {
        String rawCode = "TESTEx@$";
        String cleanCode = "TESTEX";

        CouponDTO dto = new CouponDTO(rawCode, "Desc", new BigDecimal("10.0"), LocalDate.now().plusDays(1), true);

        Coupon savedCoupon = new Coupon();
        savedCoupon.setId(1L);
        savedCoupon.setCode(cleanCode);
        savedCoupon.setDescription("Desc");
        savedCoupon.setDiscountValue(new BigDecimal("10.0"));
        savedCoupon.setExpirationDate(LocalDate.now().plusDays(1));
        savedCoupon.setActive(true);

        when(formatter.format(rawCode)).thenReturn(cleanCode);
        when(repository.save(any(Coupon.class))).thenReturn(savedCoupon);

        Coupon result = service.create(dto);

        assertEquals(cleanCode, result.getCode());

        verify(formatter).format(rawCode);
        verify(repository).save(any(Coupon.class));
    }

    @Test
    void shouldThrowErrorWhenFormatterFails() {
        String invalidCode = "ABC";
        CouponDTO dto = new CouponDTO(invalidCode, "Desc", new BigDecimal("10.0"), LocalDate.now().plusDays(1), true);

        when(formatter.format(invalidCode)).thenThrow(new InvalidCouponCodeException("Erro de tamanho"));

        assertThrows(InvalidCouponCodeException.class, () -> service.create(dto));

        verify(repository, never()).save(any());
    }

    @Test
    void shouldSoftDeleteCoupon() {
        Coupon activeCoupon = new Coupon();
        activeCoupon.setId(1L);
        activeCoupon.setActive(true);

        when(repository.findById(1L)).thenReturn(Optional.of(activeCoupon));

        service.delete(1L);

        assertFalse(activeCoupon.isActive());
        verify(repository).save(activeCoupon);
    }

    @Test
    void shouldNotDeleteAlreadyDeletedCoupon() {
        Coupon deletedCoupon = new Coupon();
        deletedCoupon.setId(1L);
        deletedCoupon.setActive(false);

        when(repository.findById(1L)).thenReturn(Optional.of(deletedCoupon));

        assertThrows(CouponAlreadyDeletedException.class, () -> service.delete(1L));

        verify(repository, never()).save(deletedCoupon);
    }
}