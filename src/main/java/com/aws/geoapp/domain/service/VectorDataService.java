package com.aws.geoapp.domain.service;

import com.aws.geoapp.domain.entity.VectorData;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Mono;

public interface VectorDataService {
    Mono<VectorData> save(Mono<FilePart> filePartMono);
    VectorData findById(String id);
    VectorData findAll();
}
