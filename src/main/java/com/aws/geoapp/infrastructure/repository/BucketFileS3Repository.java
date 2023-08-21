package com.aws.geoapp.infrastructure.repository;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.aws.geoapp.domain.repository.BucketFileRepository;
import com.aws.geoapp.infrastructure.configuration.AWSProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Repository;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;

@Builder
@Repository
@AllArgsConstructor
public class BucketFileS3Repository implements BucketFileRepository {

    private final AmazonS3 amazonS3;
    private final AWSProperties awsProperties;

    private byte[] convertFilePartToByteArray(FilePart filePart) throws IOException {
        Path path = Paths.get(filePart.filename());
        File file = Files.createFile(path).toFile();
        filePart.transferTo(file).block();
        return Files.readAllBytes(file.toPath());
    }

    @Override
    public Optional<PutObjectResult> store(FilePart filePart) throws IOException {
        String bucketName = awsProperties.getBucketName();
        String path = awsProperties.getS3BucketPath() + filePart.filename();

        byte[] bytes = convertFilePartToByteArray(filePart);
        InputStream inputStream = new ByteArrayInputStream(bytes);
        ObjectMetadata metadata = new ObjectMetadata();

        return Optional.of(amazonS3.putObject(bucketName, path, inputStream, metadata));
    }

    @Override
    public ObjectListing getBucketObjects(String bucketName, String path) {
        return amazonS3.listObjects(bucketName, path);
    }

    @Override
    public URL getUrl(String bucketName, String key) {
        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(
                bucketName, key)
                .withMethod(HttpMethod.GET)
                .withExpiration(Date.from(Instant.now().plus(Duration.ofHours(2))));

        return amazonS3.generatePresignedUrl(generatePresignedUrlRequest);
    }
}
