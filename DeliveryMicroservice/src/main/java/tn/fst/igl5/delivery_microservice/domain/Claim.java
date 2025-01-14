package tn.fst.igl5.delivery_microservice.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import tn.fst.igl5.delivery_microservice.model.Decision;
import tn.fst.igl5.delivery_microservice.model.Status;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Claim {
    @Id
    @Column(nullable = false, updatable = false)
    private String id;
    @Column(nullable = false)
    private String content;
    @Column
    @Enumerated(EnumType.STRING)
    private Decision decision;
    @Column
    @Enumerated(EnumType.STRING)
    private Status status;
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;
    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;
}
