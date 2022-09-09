package com.dimevision.redmadrobots.model.dto.image;

/**
 * @author Dimevision
 * @version 0.1
 */

public record ImageDTO(
        Long id,
        String name,
        String type
) {

    @Override
    public String toString() {
        return "ImageDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ImageDTO imageDTO)) return false;

        return id.equals(imageDTO.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
