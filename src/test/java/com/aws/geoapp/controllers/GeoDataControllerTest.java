package com.aws.geoapp.controllers;

import com.aws.geoapp.application.VectorDataController;
import com.aws.geoapp.domain.entity.BucketFile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.MultiValueMap;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GeoDataControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @LocalServerPort
    private int port;


    @BeforeEach
    void setUp() {
        webTestClient = WebTestClient.bindToServer()
                .baseUrl("http://localhost:" + port)
                .build();
    }

    @Test
    @DisplayName("Upload file to aws")
    @Disabled
    void upload() {
        webTestClient.post().uri(VectorDataController.BASE_URL + "/upload")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .bodyValue(generateBody())
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody()
                .returnResult()
                .getResponseBody();
    }

    @Test
    @DisplayName("Get all objects from aws")
    @Disabled
    void getBucketObject() {
        webTestClient.get().uri(VectorDataController.BASE_URL + "/bucket-objects")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(BucketFile.class)
                .returnResult()
                .getResponseBody();
    }

    private MultiValueMap<String, HttpEntity<?>> generateBody() {
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("file", new ClassPathResource("data/test.png"));
        return builder.build();
    }
}