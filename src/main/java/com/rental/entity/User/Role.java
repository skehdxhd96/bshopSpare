package com.rental.entity.User;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    SELLER("SELLER", "판매자"),
    BUYER("BUYER", "구매자");

    private final String key;
    private final String value;
}
