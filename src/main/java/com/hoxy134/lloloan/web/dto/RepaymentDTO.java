package com.hoxy134.lloloan.web.dto;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class RepaymentDTO implements Serializable {

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @Setter
    public static class Request {

        private BigDecimal repaymentAmount;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @Setter
    public static class Response {

        private Long applicationId;

        private BigDecimal repaymentAmount;

        private BigDecimal balance;

        private LocalDateTime createdAt;

        private LocalDateTime updatedAt;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @Setter
    public static class ListResponse {

        private Long repaymentId;

        private BigDecimal repaymentAmount;

        private LocalDateTime createdAt;

        private LocalDateTime updatedAt;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @Setter
    public static class UpdateResponse {

        private Long applicationId;

        private BigDecimal beforeRepaymentAmount;

        private BigDecimal afterRepaymentAmount;

        private BigDecimal balance;

        private LocalDateTime createdAt;

        private LocalDateTime updatedAt;
    }

}
