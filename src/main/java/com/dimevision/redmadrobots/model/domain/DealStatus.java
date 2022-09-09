package com.dimevision.redmadrobots.model.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Dimevision
 * @version 0.1
 */

@AllArgsConstructor
@Getter
public enum DealStatus {
    NON_BOOKED(0),
    BOOKED(1);

    private final int statusCode;
}
