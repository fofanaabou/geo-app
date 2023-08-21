package com.aws.geoapp.services;

import com.amazonaws.services.s3.AmazonS3;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.codec.multipart.FilePart;

import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class GeoDataServiceTest {

    public static final String STATIC_UPLOAD = "";


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
    @Disabled
    void store() {

    }

    @Test
    @Disabled
    void getBucketObject() {
        // given

    }
}