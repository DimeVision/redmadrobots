package com.dimevision.redmadrobots.model.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.time.Instant;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * @author Dimevision
 * @version 0.1
 */

@MappedSuperclass
@Getter
@Setter
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    protected Long id;

    @CreationTimestamp
    @Column(name = "created_at")
    protected Instant createdAt;

    @UpdateTimestamp
    @Column(name = "modified_at")
    protected Instant modifiedAt;

    @Override
    public String toString() {
        return "BaseEntity{" +
                "id=" + id +
                '}';
    }
}
