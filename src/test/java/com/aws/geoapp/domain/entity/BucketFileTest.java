package com.aws.geoapp.domain.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BucketFileTest {

    public static final String SOME_VERSION = "someVersion";
    public static final String SOME_NAME = "SomeName";
    public static final String SOME_URL = "someUrl";
    public static final String SOME_KEY = "someKey";
    private static BucketFile bucketFile;

    @BeforeEach
    void setUp() {
        bucketFile = BucketFile.builder()
                .versionId(SOME_VERSION)
                .name(SOME_NAME)
                .presignedUrl(SOME_URL)
                .key(SOME_KEY)
                .build();
    }

    @Test
    @DisplayName("Test bucket creation")
    void createBucketTest() {

        assertAll("Test BucketCreation",
                () -> assertEquals(SOME_NAME, bucketFile.getName()),
                () -> assertEquals(SOME_VERSION, bucketFile.getVersionId()),
                () -> assertEquals(SOME_URL, bucketFile.getPresignedUrl()),
                () -> assertEquals(SOME_KEY, bucketFile.getKey())
        );
    }

    @Test
    void bucketAccessorTest() {
        assertAll("Test BucketCreation",
                () -> {
                    bucketFile.setName("data-file");
                    assertEquals("data-file", bucketFile.getName());
                },
                () -> {
                    bucketFile.setName("data-file");
                    assertEquals("data-file", bucketFile.getName());
                },
                () -> {
                    bucketFile.setName("data-file");
                    assertEquals("data-file", bucketFile.getName());
                },
                () -> {
                    bucketFile.setName("data-file");
                    assertEquals("data-file", bucketFile.getName());
                }
        );
    }

}