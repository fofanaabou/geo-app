package com.aws.geoapp.services;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.aws.geoapp.configuration.AWSProperties;
import com.aws.geoapp.models.BucketObjectInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.slf4j.Logger;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

@Service
@Builder
@AllArgsConstructor
public class GeoDataService implements StorageService {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(GeoDataService.class);

    private final AwsConnection awsConnection;
    private final AWSProperties awsProperties;


    private byte[] convertFilePartToByteArray(FilePart filePart) throws IOException {
        Path root = Paths.get(awsProperties.getLocalUploadPath());
        File file = new File(root.resolve(filePart.filename()).toUri());
        filePart.transferTo(file).block();
        return Files.readAllBytes(file.toPath());
    }

    @Override
    public Mono<PutObjectResult> store(Mono<FilePart> filePart) {
        AmazonS3 awsAmazon3 = awsConnection.getClient();
        return filePart.flatMap(f -> {
            try {
                return uploadFile(f, awsAmazon3);
            } catch (IOException e) {
                return Mono.error(new RuntimeException(e));
            }
        });
    }

    private Mono<PutObjectResult> uploadFile(FilePart f, AmazonS3 awsAmazon3) throws IOException {
        byte[] fileData = convertFilePartToByteArray(f);
        String key = awsProperties.getS3BucketPath() + f.filename();
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(fileData.length);
        PutObjectResult putObjectResult = awsAmazon3.putObject(awsProperties.getBucketName(), key, new ByteArrayInputStream(fileData), metadata);
        deleteFileCreatedAfterUpload();
        return Mono.just(putObjectResult);
    }

    private void deleteFileCreatedAfterUpload() throws IOException {
        File directory = Paths.get(awsProperties.getLocalUploadPath()).toFile();
        if (directory.exists()) {
            File[] files = directory.listFiles();
            assert files != null;
            for (File f : files) {
                Files.deleteIfExists(f.toPath());
            }
        }
    }

    @Override
    public Flux<BucketObjectInfo> getBucketObject() {
        AmazonS3 awsAmazon3 = awsConnection.getClient();
        ObjectListing objectListing = awsAmazon3.listObjects(awsProperties.getBucketName(), awsProperties.getS3BucketPath());

        return Flux.fromIterable(objectListing.getObjectSummaries())
                .map(os -> {
                    URL url = getUrl(awsAmazon3, os.getKey());
                    return createAndInitializeBucketInfo(os, url);
                });
    }

    private static BucketObjectInfo createAndInitializeBucketInfo(S3ObjectSummary os, URL url) {
        BucketObjectInfo bucketObjectInfo = new BucketObjectInfo();
        bucketObjectInfo.setKey(os.getKey());
        bucketObjectInfo.setObjectUrl(url.toString());
        return bucketObjectInfo;
    }

    private URL getUrl(AmazonS3 awsAmazon3, String key) {
        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(
                awsProperties.getBucketName(), key)
                .withMethod(HttpMethod.GET)
                .withExpiration(Date.from(Instant.now().plus(Duration.ofHours(2))));

        return awsAmazon3.generatePresignedUrl(generatePresignedUrlRequest);
    }

}
