package tn.fst.igl5.delivery_microservice.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.time.OffsetDateTime;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import tn.fst.igl5.delivery_microservice.model.Vehicule;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class DeliveryPerson {
    @Id
    @Column(nullable = false, updatable = false)
    private String id;
    @Column(nullable = false)
    private String name;
    @Column
    private String phone;
    @Column
    private String email;
    @Column
    private String address;
    @Column
    @Enumerated(EnumType.STRING)
    private Vehicule vehiculeType;
    @Column
    private String plateNumber;
    @OneToMany(mappedBy = "deliveryPerson")
    private Set<Delivery> deliveries;
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;
    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;
}
