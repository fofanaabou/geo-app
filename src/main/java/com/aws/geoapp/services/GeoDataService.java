package com.aws.geoapp.services;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.aws.geoapp.configuration.AWSProperties;
import com.aws.geoapp.models.BucketObjectInfo;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

@Service
@AllArgsConstructor
public class GeoDataService implements StorageService {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(GeoDataService.class);

    private final AwsConnection awsConnection;
    private final AWSProperties awsProperties;


    private File convertFilePartToByteArray(FilePart filePart) {
        Path root = Paths.get(awsProperties.getLocalUploadPath());
        File file = new File(root.resolve(filePart.filename()).toUri());
        filePart.transferTo(file).then().block();
        return file;
    }

    @Override
    public Mono<Void> store(Mono<FilePart> filePart) {
        return filePart.doOnNext(ps -> log.info(ps.filename()))
                .flatMap(f -> {
                    uploadFile(f);
                    return Mono.empty();
                })
                .then();
    }

    private void uploadFile(FilePart f) {
        File file = convertFilePartToByteArray(f);
        AmazonS3 awsAmazon3 = awsConnection.getClient();
        awsAmazon3.putObject(awsProperties.getBucketName(), awsProperties.getS3BucketPath()+ f.filename(), file);
    }

    @Override
    public Flux<BucketObjectInfo> getBucketObject() {

        AmazonS3 awsAmazon3 = awsConnection.getClient();
        ObjectListing objectListing = awsAmazon3.listObjects(awsProperties.getBucketName(), awsProperties.getS3BucketPath());

        return  Flux.fromIterable(objectListing.getObjectSummaries())
                .map(os -> {
                    URL url = getUrl(awsAmazon3,  os.getKey());
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
