package com.suggestions.gendra.dto;

import com.suggestions.gendra.entity.Geoname;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Builder
public class SuggestionsDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private String latitude;
    private String longitude;
    private double score;

    public static SuggestionsDTO build(final Geoname geoname, final double score) {
        return SuggestionsDTO.builder()
                .name(geoname.getName())
                .latitude(geoname.getLatitude().toString())
                .longitude(geoname.getLongitude().toString())
                .score(score)
                .build();
    }
}
