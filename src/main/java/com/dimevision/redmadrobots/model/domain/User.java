package com.dimevision.redmadrobots.model.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User extends BaseEntity {

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    @Enumerated
    private Role role;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private Set<Advertisement> advertisements = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;

        return getId().equals(user.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }
}