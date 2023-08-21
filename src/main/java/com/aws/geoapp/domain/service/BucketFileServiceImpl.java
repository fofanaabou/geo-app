package com.aws.geoapp.domain.service;

import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.aws.geoapp.domain.entity.BucketFile;
import com.aws.geoapp.domain.repository.BucketFileRepository;
import com.aws.geoapp.infrastructure.configuration.AWSProperties;
import lombok.AllArgsConstructor;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@AllArgsConstructor
public class BucketFileServiceImpl implements BucketFileService {

    private final BucketFileRepository bucketFileRepository;
    private final AWSProperties awsProperties;

    @Override
    public Mono<BucketFile> store(Mono<FilePart> filePart) {
        return filePart.flatMap(file -> {
            try {
                return uploadFile(file);
            } catch (IOException e) {
                return Mono.error(new RuntimeException(e));
            }
        });
    }

    private Mono<? extends BucketFile> uploadFile(FilePart file) throws IOException {
        String path = awsProperties.getS3BucketPath() + file.filename();
        BucketFile bucketFile = BucketFile.builder().name(file.filename()).key(path).build();
        bucketFileRepository.store(file)
                .ifPresent(p -> bucketFile.setVersionId(p.getVersionId()));

        deleteFileCreatedAfterUpload(Paths.get(file.filename()));

        return Mono.just(bucketFile);
    }

    private void deleteFileCreatedAfterUpload(Path path) throws IOException {
        Files.deleteIfExists(path);
    }



    @Override
    public Flux<BucketFile> getBucketObjects() {
        ObjectListing objectListing = bucketFileRepository
                .getBucketObjects(awsProperties.getBucketName(), awsProperties.getS3BucketPath());
        return Flux.fromIterable(objectListing.getObjectSummaries())
                .map(os -> {
                    URL url = getUrl(os.getKey());
                    return getBucketFileFromObject(os, url);
                });
    }

    private URL getUrl(String key) {
        return bucketFileRepository.getUrl(awsProperties.getBucketName(), key);
    }

    private BucketFile getBucketFileFromObject(S3ObjectSummary os, URL url) {
        return BucketFile.builder()
                .key(os.getKey())
                .presignedUrl(url.toString())
                .build();
    }
}
