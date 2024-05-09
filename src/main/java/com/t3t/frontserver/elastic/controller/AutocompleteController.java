package com.t3t.frontserver.elastic.controller;

import com.t3t.frontserver.elastic.client.ElasticClient;
import com.t3t.frontserver.model.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AutocompleteController {
    private final ElasticClient elasticAdaptor;


    @GetMapping("/autocomplete")
    public ResponseEntity<BaseResponse<List<String>>> proxyAutocomplete(@RequestParam String prefix) {
        return elasticAdaptor.autocomplete(prefix);
    }
}