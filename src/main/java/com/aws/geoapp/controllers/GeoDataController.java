package com.aws.geoapp.controllers;

import com.amazonaws.services.s3.model.PutObjectResult;
import com.aws.geoapp.models.BucketObjectInfo;
import com.aws.geoapp.services.StorageService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequestMapping(GeoDataController.BASE_URL)
@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
public class GeoDataController {

    private final StorageService storageService;
    public static final String BASE_URL = "buckets";

    @PostMapping(value = "/upload")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Mono<PutObjectResult> upload(@RequestPart("file") Mono<FilePart> file) {
        return storageService.store(file);
    }

    @GetMapping(value = "/bucket-objects")
    public Flux<BucketObjectInfo> getBucketObject() {
        return storageService.getBucketObject();
    }
}
