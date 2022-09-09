package com.dimevision.redmadrobots.model.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "advertisements")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Advertisement extends BaseEntity {

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "contact")
    private String contact;

    @Column(name = "status")
    @Enumerated
    private Status status;

    @Column(name = "deal_status")
    @Enumerated
    private DealStatus dealStatus;

    @Column(name = "customer_id")
    private Long customerId;
    @ManyToOne
    @JoinColumn(name = "users_id")
    @JsonBackReference
    private User user;

    @OneToMany(mappedBy = "advertisements", cascade = {CascadeType.REMOVE, CascadeType.REFRESH, CascadeType.PERSIST})
    @JsonManagedReference
    private Set<Image> images = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Advertisement that)) return false;

        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }
}