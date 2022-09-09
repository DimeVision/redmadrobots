package com.dimevision.redmadrobots.model.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Dimevision
 * @version 0.1
 */

@AllArgsConstructor
@Getter
public enum Role {
    ADMINISTRATOR(0),
    USER(1);

    private final int roleCode;
}
