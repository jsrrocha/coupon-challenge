package com.challenge.coupon.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
public record CouponDTO(
        @NotBlank(message = "O código é obrigatório")
        String code,

        @NotBlank(message = "A descrição é obrigatória")
        String description,

        @NotNull(message = "O valor do desconto é obrigatório")
        @DecimalMin(value = "0.5", message = "O desconto mínimo deve ser de 0.5")
        BigDecimal discountValue,

        @FutureOrPresent(message = "A data de expiração não pode ser no passado")
        @NotNull(message = "A data de expiração é obrigatória")
        LocalDate expirationDate,

        boolean published
) {}