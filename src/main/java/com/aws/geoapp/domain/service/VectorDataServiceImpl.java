package com.aws.geoapp.domain.service;

import com.aws.geoapp.domain.entity.BucketFile;
import com.aws.geoapp.domain.entity.Format;
import com.aws.geoapp.domain.entity.VectorData;
import com.aws.geoapp.domain.repository.VectorDataRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@AllArgsConstructor
public class VectorDataServiceImpl implements VectorDataService {

    private final BucketFileService bucketFileService;

    private final VectorDataRepository vectorDataRepository;
    @Override
    public Mono<VectorData> save(Mono<FilePart> filePartMono) {
        Mono<BucketFile> bucketMono = bucketFileService.store(filePartMono);
        return bucketMono.flatMap(this::create);
    }

    private Mono<? extends VectorData> create(BucketFile bucketFile) {
        VectorData vectorData = VectorData.builder()
                .createdAt(LocalDateTime.now().toString())
                .bucketFileUrl(bucketFile.getKey())
                .format(Format.GEOJSON.name())
                .name(bucketFile.getName())
                .build();

        return Mono.just(vectorDataRepository.save(vectorData));
    }

    @Override
    public VectorData findById(String id) {
        return null;
    }

    @Override
    public VectorData findAll() {
        return null;
    }
}
