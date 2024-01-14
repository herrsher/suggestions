package com.suggestions.gendra.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Geoname {
    private String name;
    private String latitude;
    private String longitude;
    private String country;
    private String fipscode;

    public Geoname(final String[] data) {
        this.name = data[1];
        this.latitude = data[4];
        this.longitude = data[5];
        this.country = data[8];
        this.fipscode = data[10];
    }
}
