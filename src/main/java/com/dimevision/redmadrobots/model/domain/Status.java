package com.dimevision.redmadrobots.model.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Dimevision
 * @version 0.1
 */

@AllArgsConstructor
@Getter
public enum Status {
    INACTIVE(0),
    ACTIVE(1);

    private final int statusCode;
}
