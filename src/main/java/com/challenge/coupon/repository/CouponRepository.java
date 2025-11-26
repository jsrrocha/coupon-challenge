package com.challenge.coupon.repository;

import com.challenge.coupon.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
    Optional<Coupon> findByIdAndActiveTrue(Long id);
}