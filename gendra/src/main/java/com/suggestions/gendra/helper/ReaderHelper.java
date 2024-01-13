package com.suggestions.gendra.helper;

import com.suggestions.gendra.entity.Geoname;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class ReaderHelper {
    public static List<Geoname> readCities() throws IOException {
        final List<Geoname> cities = new ArrayList<>();
        final String route = "";

        try (BufferedReader br = new BufferedReader(new FileReader(route))) {
            br.lines().forEach(row -> {
                final String[] data = row.split("\t");
                final Geoname geoname = new Geoname(data);
                cities.add(geoname);
            });
        }

        return cities;
    }
}
