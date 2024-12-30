package com.hoxy134.lloloan.db.entity;

import com.hoxy134.lloloan.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;

@Getter
@Setter
@Builder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DynamicInsert
@DynamicUpdate
@Where(clause = "is_deleted=false")
public class Terms extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    @EqualsAndHashCode.Include
    private Long termsId;

    @Column(columnDefinition = "varchar(500) NOT NULL COMMENT '약관'")
    private String name;

    @Column(columnDefinition = "varchar(1000) NOT NULL COMMENT '약관상세 URL'")
    private String termsDetailUrl;
}
