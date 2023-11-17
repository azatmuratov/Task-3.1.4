package com.itmentor.spring_boot.task.rest;

import com.itmentor.spring_boot.task.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import java.util.List;
import java.util.Objects;

@RestController
public class UserRestController {

    private final RestTemplate restTemplate;
    private final HttpHeaders headers;
    private final String URL = "http://94.198.50.185:7081/api/users";


    @Autowired
    public UserRestController(RestTemplate restTemplate, HttpHeaders headers) {
        this.restTemplate = restTemplate;
        this.headers = headers;
        this.headers.set("Cookie",
                String.join(";",
                        Objects.requireNonNull(restTemplate.headForHeaders(URL)
                                .get("Set-Cookie"))));
    }


    @GetMapping
    public List<User> getAllUser() {
        URI uri = UriComponentsBuilder.fromUriString(URL).build().toUri();
        ResponseEntity<List<User>> responseEntity = restTemplate.exchange(
                uri, HttpMethod.GET, null, new ParameterizedTypeReference<>() {});
        System.out.println(responseEntity.getHeaders());
        return responseEntity.getBody();
    }

    @PostMapping
    public ResponseEntity<String> createUser() {
        User newUser = new User(3L,"James", "Brown", (byte) 20);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<User> request = new HttpEntity<>(newUser, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(URL,
                HttpMethod.POST, request, String.class);
        System.out.println("Response: " + responseEntity.getBody());
        return responseEntity;
    }

    @PutMapping
    public ResponseEntity<String> updateUser() {
        User newUser = new User(3L,"Thomas", "Shelby", (byte) 20);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<User> request = new HttpEntity<>(newUser, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(URL,
                HttpMethod.PUT, request, String.class, 3L);
        System.out.println("Response: " + responseEntity.getBody());
        return responseEntity;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id) {
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(URL + "/"
                + id, HttpMethod.DELETE, entity, String.class);
        System.out.println("Response: " + responseEntity.getBody());
        return responseEntity;
    }
}
