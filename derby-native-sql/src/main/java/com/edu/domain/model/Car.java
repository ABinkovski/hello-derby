package com.edu.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@Builder
@RequiredArgsConstructor
public class Car {
    private final Integer id;
    private final String name;
    private final Integer price;
}
