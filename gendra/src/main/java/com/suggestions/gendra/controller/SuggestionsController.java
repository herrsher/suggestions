package com.suggestions.gendra.controller;

import com.suggestions.gendra.dto.SuggestionsDTO;
import com.suggestions.gendra.service.SuggestionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/suggestions")
@RequiredArgsConstructor
public class SuggestionsController {

    private final SuggestionsService suggestionsService;

    @GetMapping()
    public ResponseEntity<List<SuggestionsDTO>> getAllSugestions(
            @RequestParam(value = "q") final String q,
            @RequestParam(value = "latitude", required = false) final String latitude,
            @RequestParam(value = "longitude", required = false) final String longitude) throws IOException {

        return ResponseEntity.ok(suggestionsService.getSuggestions(q, latitude, longitude));
    }
}
