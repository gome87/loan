package com.hoxy134.lloloan.web.dto;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class EntryDTO implements Serializable {

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @Setter
    public static class Request{

        private BigDecimal entryAmount;
    }


    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @Setter
    public static class Response {

        private Long entryId;

        private Long applicationId;

        private BigDecimal entryAmount;

        private LocalDateTime createdAt;

        private LocalDateTime updatedAt;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @Setter
    public static class UpdateResponse {

        private Long entryId;

        private Long applicationId;

        private BigDecimal beforeEntryAmount;

        private BigDecimal afterEntryAmount;

        private LocalDateTime createdAt;

        private LocalDateTime updatedAt;
    }


}
