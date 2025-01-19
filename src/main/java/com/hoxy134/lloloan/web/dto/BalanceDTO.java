package com.hoxy134.lloloan.web.dto;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class BalanceDTO implements Serializable {

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @Setter
    public static class Request {

        private Long applicationId;

        private BigDecimal enrtyAmount;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @Setter
    public static class Response {

        private Long balanceId;

        private Long applicationId;

        private BigDecimal balance;

        private LocalDateTime createdAt;

        private LocalDateTime updatedAt;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @Setter
    public static class UpdateRequest {

        private Long applicationId;

        private BigDecimal beforeEnrtyAmount;

        private BigDecimal afterEnrtyAmount;
    }

}
