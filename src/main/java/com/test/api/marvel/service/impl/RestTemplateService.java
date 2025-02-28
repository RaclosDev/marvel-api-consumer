package com.test.api.marvel.service.impl;

import com.test.api.marvel.exception.ApiErrorException;
import com.test.api.marvel.service.HttpClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@Service
public class RestTemplateService implements HttpClientService {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public <T> T doGet(String endpoint, Map<String, String> queryParams, Class<T> responseType) {
        URI finalUri = BuildFinalUri(endpoint, queryParams);

        HttpEntity<?> httpEntity = new HttpEntity<>(getHeaders());
        ResponseEntity<T> response = restTemplate.exchange(finalUri, HttpMethod.GET, httpEntity, responseType);

        if (!response.getStatusCode().is2xxSuccessful()) {
            String msg = String.format("Error en el endpoint [{} - {}] codigo de respuesta: {}", HttpMethod.GET, endpoint, response.getStatusCode());
            throw new ApiErrorException();
        }

        return response.getBody();
    }

    private static URI BuildFinalUri(String endpoint, Map<String, String> queryParams) {
        URI uri = URI.create(endpoint);
        UriComponentsBuilder builder = UriComponentsBuilder.fromUri(uri);

        if (queryParams != null) {
            queryParams.forEach(builder::queryParam);
        }

        URI finalUri = builder.build().toUri();
        return finalUri;
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", "application/json");
        headers.add("Content-Type", "application/json");
        return headers;
    }

    @Override
    public <T, R> T doPost(String endpoint, Map<String, String> queryParams, Class<T> responseType, R requestBody) {

        URI finalUri = BuildFinalUri(endpoint, queryParams);

        HttpEntity<?> httpEntity = new HttpEntity<>(requestBody, getHeaders());

        ResponseEntity<T> response = restTemplate.exchange(finalUri, HttpMethod.POST, httpEntity, responseType);

        if (!response.getStatusCode().is2xxSuccessful()) {
            String msg = String.format("Error en el endpoint [{} - {}] codigo de respuesta: {}", HttpMethod.POST, endpoint, response.getStatusCode());
            throw new ApiErrorException();
        }

        return response.getBody();
    }

    @Override
    public <T, R> T doPut(String endpoint, Map<String, String> queryParams, Class<T> responseType, R requestBody) {

        URI finalUri = BuildFinalUri(endpoint, queryParams);

        HttpEntity<?> httpEntity = new HttpEntity<>(requestBody, getHeaders());

        ResponseEntity<T> response = restTemplate.exchange(finalUri, HttpMethod.PUT, httpEntity, responseType);

        if (!response.getStatusCode().is2xxSuccessful()) {
            String msg = String.format("Error en el endpoint [{} - {}] codigo de respuesta: {}", HttpMethod.PUT, endpoint, response.getStatusCode());
            throw new ApiErrorException();
        }

        return response.getBody();
    }

    @Override
    public <T> T doDelete(String endpoint, Map<String, String> queryParams, Class<T> responseType) {
        URI finalUri = BuildFinalUri(endpoint, queryParams);

        HttpEntity<?> httpEntity = new HttpEntity<>(getHeaders());
        ResponseEntity<T> response = restTemplate.exchange(finalUri, HttpMethod.DELETE, httpEntity, responseType);

        if (!response.getStatusCode().is2xxSuccessful()) {
            String msg = String.format("Error en el endpoint [{} - {}] codigo de respuesta: {}", HttpMethod.DELETE, endpoint, response.getStatusCode());
            throw new ApiErrorException();
        }

        return response.getBody();
    }
}
