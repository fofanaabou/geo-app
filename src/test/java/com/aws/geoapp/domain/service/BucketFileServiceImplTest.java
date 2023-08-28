package com.aws.geoapp.domain.service;

import com.amazonaws.services.s3.model.PutObjectResult;
import com.aws.geoapp.domain.entity.BucketFile;
import com.aws.geoapp.domain.repository.BucketFileRepository;
import com.aws.geoapp.infrastructure.configuration.AWSProperties;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BucketFileServiceImplTest {

    public static final String STATIC_UPLOAD = "src/test/resources/static/upload/";
    public static final String FILE_NAME = "test.txt";

    public static final String ANY_VERSION = "ANY VERSION";
    @Mock
    private BucketFileRepository bucketFileRepository;

    @Mock
    private AWSProperties awsProperties;
    @Mock
    private FilePart filePart;

    private BucketFileService bucketFileService;



    @BeforeEach
    void setUp() {
        bucketFileService = new BucketFileServiceImpl(bucketFileRepository, awsProperties);
    }

    @Test
    void shouldInvokeStoreOnce() throws IOException {
        BucketFile bucketFileOne = BucketFile.builder()
                .name(FILE_NAME)
                .key(String.join("", STATIC_UPLOAD, FILE_NAME))
                .build();

        PutObjectResult objectResult = new PutObjectResult();
        objectResult.setContentMd5(ANY_VERSION);


        when(awsProperties.getS3BucketPath()).thenReturn(STATIC_UPLOAD);

        when(filePart.filename()).thenReturn(FILE_NAME);

        when(bucketFileRepository.store(any(FilePart.class))).thenReturn(Optional.of(objectResult));

        Mono<BucketFile> bucketFileMono = bucketFileService.store(Mono.just(filePart));



        StepVerifier.create(bucketFileMono)
                .expectNextMatches(bucketFile -> bucketFileOne.getName().equals(bucketFile.getName()))
                .expectComplete()
                .verify();

        verify(bucketFileRepository, times(1)).store(any());
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Path.of(FILE_NAME));
    }
}