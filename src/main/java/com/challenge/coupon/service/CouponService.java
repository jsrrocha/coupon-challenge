package com.challenge.coupon.service;

import com.challenge.coupon.controller.CouponCodeFormatter;
import com.challenge.coupon.dto.CouponDTO;
import com.challenge.coupon.entity.Coupon;
import com.challenge.coupon.exception.CouponAlreadyDeletedException;
import com.challenge.coupon.exception.CouponNotFoundException;
import com.challenge.coupon.exception.InvalidCouponCodeException;
import com.challenge.coupon.repository.CouponRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class CouponService {

    private final CouponRepository repository;
    private final CouponCodeFormatter formatter;

    public CouponService(CouponRepository repository, CouponCodeFormatter formatter) {
        this.repository = repository;
        this.formatter = formatter;
    }

    public Coupon create(CouponDTO dto) {
        Coupon coupon = new Coupon();
        BeanUtils.copyProperties(dto, coupon);

        String formattedCode = formatter.format(dto.code());

        if (formattedCode.length() != 6) {
            throw new InvalidCouponCodeException(
                "O código deve ter exatamente 6 caracteres alfanuméricos após formatação.");
        }

        coupon.setCode(formattedCode);
        coupon.setActive(true);

        return repository.save(coupon);
    }

    public void delete(Long id) {
        Coupon coupon = repository.findById(id)
                .orElseThrow(() ->
                        new CouponNotFoundException(id));

        if (!coupon.isActive()) {
            throw new CouponAlreadyDeletedException(id);
        }

        coupon.setActive(false);
        repository.save(coupon);
    }
}