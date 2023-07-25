package com.aws.geoapp.services;

import com.aws.geoapp.models.BucketObjectInfo;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface StorageService {
    Mono<Void> store(Mono<FilePart> filePart);
    Flux<BucketObjectInfo> getBucketObject();

}
