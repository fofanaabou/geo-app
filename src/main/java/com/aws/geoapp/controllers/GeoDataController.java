package com.aws.geoapp.controllers;

import com.aws.geoapp.services.StorageService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RequestMapping(GeoDataController.BASE_URL)
@RestController
@AllArgsConstructor
public class GeoDataController {

    private final StorageService storageService;
    private static final Logger log = LoggerFactory.getLogger(GeoDataController.class);
    public static final String BASE_URL = "geo-data";

    @PostMapping(value = "/upload")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Mono<Void> upload(@RequestPart("file") Mono<FilePart> file) {
        return storageService.store(file);
    }
}
