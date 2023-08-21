package com.aws.geoapp.application;

import com.aws.geoapp.domain.entity.VectorData;
import com.aws.geoapp.domain.service.VectorDataService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RequestMapping(VectorDataController.BASE_URL)
@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
public class VectorDataController {
    public static final String BASE_URL = "api/v1/buckets";

    private final VectorDataService vectorDataService;

    @PostMapping(value = "/upload")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Mono<VectorData> upload(@RequestPart("file") Mono<FilePart> file) {
        return vectorDataService.save(file);
    }
}
