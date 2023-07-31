package com.aws.geoapp.services;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.aws.geoapp.configuration.AWSProperties;
import com.aws.geoapp.models.BucketObjectInfo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.ByteArrayInputStream;
import java.io.File;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GeoDataServiceTest {

    public static final String STATIC_UPLOAD = "";
    @Mock
    private AwsConnection awsConnection;

    @Mock
    private AWSProperties awsProperties;

    @InjectMocks
    private GeoDataService geoDataService;

    private AmazonS3 s3Client;


    private FilePart filePart;

    @BeforeEach
    void setUp() {
        s3Client = mock(AmazonS3.class);
        filePart = mock(FilePart.class);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void store() {
        PutObjectResult putObjectResult = new PutObjectResult();
        when(awsConnection.getClient()).thenReturn(s3Client);
        when(awsProperties.getBucketName()).thenReturn("test");
        when(awsProperties.getS3BucketPath()).thenReturn(STATIC_UPLOAD);
        when(s3Client.putObject(anyString(), anyString(), any(ByteArrayInputStream.class), any(ObjectMetadata.class)))
                .thenReturn(putObjectResult);
        when(filePart.transferTo(any(File.class))).thenReturn(Mono.empty());
        when(filePart.filename()).thenReturn("test.txt");

        // when
        Mono<PutObjectResult> result = geoDataService.store(Mono.just(filePart));
        // then
        StepVerifier.create(result.log())
                .expectNext(putObjectResult)
                .verifyComplete();
    }

    @Test
    void getBucketObject() {
        // given

        ObjectListing objectListing = new ObjectListing();
        when(awsConnection.getClient()).thenReturn(s3Client);
        when(awsProperties.getBucketName()).thenReturn("test");
        when(awsProperties.getS3BucketPath()).thenReturn("s3path");
        when(s3Client.listObjects(anyString(), anyString())).thenReturn(objectListing);
        // when
        Flux<BucketObjectInfo> result = geoDataService.getBucketObject();
        // then
        StepVerifier.create(result.log())
                .expectNextCount(0)
                .verifyComplete();
    }
}