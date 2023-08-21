package com.aws.geoapp.domain.service;

import com.aws.geoapp.domain.entity.BucketFile;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BucketFileService {
    Mono<BucketFile> store(Mono<FilePart> filePart);
    Flux<BucketFile> getBucketObjects();
}
