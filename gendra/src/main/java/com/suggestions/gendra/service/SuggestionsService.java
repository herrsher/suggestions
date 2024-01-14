package com.suggestions.gendra.service;

import com.suggestions.gendra.dto.SuggestionsDTO;
import com.suggestions.gendra.entity.Geoname;
import com.suggestions.gendra.helper.ReaderHelper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SuggestionsService {
    private final ReaderHelper readerHelper;

    public List<SuggestionsDTO> getSuggestions(
        final String name, final String latitude, final String longitude) throws IOException {

        final List<Geoname> cities = ReaderHelper.readCities();
        final List<SuggestionsDTO> suggestionsDTOs
                = buildScoreData(name, latitude, longitude, cities);
        return suggestionsDTOs.stream()
                .sorted(Comparator.comparingDouble(SuggestionsDTO::getScore).reversed())
                .collect(Collectors.toList());
    }

    private List<SuggestionsDTO> buildScoreData(
            final String name,
            final String latitude,
            final String longitude,
            final List<Geoname> cities) {
        return cities.stream()
            .map(city -> {
                final double score
                    = generateScore(name,latitude,longitude,city);
                return SuggestionsDTO.build(city, score);
            })
            .collect(Collectors.toList());
    }

    private double generateScore(
        final String name,
        final String latitude,
        final String longitude,
        final Geoname city) {

        if (StringUtils.isNotEmpty(latitude) && StringUtils.isNotEmpty(longitude)) {
            final double firstValue = calculateOnlyNameScore(name, city.getName()) * 0.4;
            final double latitudeValue
                    = calculateProximity(latitude, city.getLatitude(), 19.50, 64.85);
            final double longitudeValue
                    = calculateProximity(longitude, city.getLongitude(), -161.75, -52.70);
            return firstValue + (latitudeValue * 0.3) + (longitudeValue * 0.3);
        } else {
            return calculateOnlyNameScore(name, city.getName());
        }
    }

    private double calculateOnlyNameScore(final String name, final String comparedName) {
        LevenshteinDistance levenshteinDistance = new LevenshteinDistance();
        final double similaritySore
                = 100 - 5 * levenshteinDistance.apply(name, comparedName);
        return similaritySore > 0 ? similaritySore / 100 : 0;
    }

    private double calculateProximity(
            final String value, final String toCompare, final double min, final double max) {
        final double range = max - min;
        if (value == toCompare) {
            return 1;
        }
        try {
            return  1 - Math.abs(
                    Double.parseDouble(value) - Double.parseDouble(toCompare)) / range;
        } catch (NumberFormatException e) {
            return 0;
        }

    }
}
