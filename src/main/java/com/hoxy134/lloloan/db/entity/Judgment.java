package com.hoxy134.lloloan.db.entity;

import com.hoxy134.lloloan.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@Where(clause = "is_deleted=false")
public class Judgment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long judgmentId;

    @Column(columnDefinition = "bigint NOT NULL COMMENT '신청 ID'")
    private Long applicationId;

    @Column(columnDefinition = "varchar(20) NOT NULL COMMENT '심사자'")
    private String name;

    @Column(columnDefinition = "decimal(18,2) DEFAULT NULL COMMENT '승인 금액'")
    private BigDecimal approvalAmount;

    @Column(columnDefinition = "decimal(5,4) DEFAULT NULL COMMENT '승인 금리'")
    private BigDecimal approvalInterestRate;

}
