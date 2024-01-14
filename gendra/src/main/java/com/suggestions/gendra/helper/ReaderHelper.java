package com.suggestions.gendra.helper;

import com.suggestions.gendra.entity.Geoname;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class ReaderHelper {
    public static List<Geoname> readCities() throws IOException {
        final List<Geoname> cities = new ArrayList<>();
        Resource resource = new ClassPathResource("data/cities_canada-usa.tsv");

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(resource.getInputStream()))) {
            br.lines().forEach(row -> {
                final String[] data = row.split("\t");
                final Geoname geoname = new Geoname(data);
                cities.add(geoname);
            });
        }

        return cities;
    }
}
