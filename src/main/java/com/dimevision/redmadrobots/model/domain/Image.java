package com.dimevision.redmadrobots.model.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.CascadeType.*;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "images")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Image {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", columnDefinition = "INT UNSIGNED not null")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "image", columnDefinition = "bytea")
    @Lob
    private byte[] imageCode;

    @ManyToOne(cascade = {REMOVE, PERSIST, REFRESH})
    @JoinColumn(name = "advertisements_id")
    @JsonBackReference
    private Advertisement advertisements;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Image image)) return false;

        return getId().equals(image.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }
}