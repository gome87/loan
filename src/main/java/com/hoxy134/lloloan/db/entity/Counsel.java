package com.hoxy134.lloloan.db.entity;

import com.hoxy134.lloloan.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@Where(clause = "is_deleted=false")
public class Counsel extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long counselId;

    @Column(columnDefinition = "datetime DEFAULT NULL COMMENT '신청일자'")
    private LocalDateTime appliedAt;

    @Column(columnDefinition = "varchar(20) DEFAULT NULL COMMENT '상담 요청자'")
    private String name;

    @Column(columnDefinition = "varchar(20) DEFAULT NULL COMMENT '전화번호'")
    private String cellPhone;

    @Column(columnDefinition = "varchar(100) DEFAULT NULL COMMENT '상담 요청자 이메일'")
    private String email;

    @Column(columnDefinition = "text DEFAULT NULL COMMENT '상담 메모'")
    private String memo;

    @Column(columnDefinition = "varchar(200) DEFAULT NULL COMMENT '주소'")
    private String address;

    @Column(columnDefinition = "varchar(1000) DEFAULT NULL COMMENT '상세 주소'")
    private String addressDetail;

    @Column(columnDefinition = "varchar(10) DEFAULT NULL COMMENT '우편번호'")
    private String zipCode;

}
