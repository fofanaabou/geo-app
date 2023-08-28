package com.aws.geoapp.infrastructure.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class VectorDataInfoTest {

    public static final String SHP = "shp";
    public static final String SOME_URL = "someUrl";
    public static final String SOME_ID = "someId";
    public static final String SOME_FILE = "someFile";
    private final LocalDateTime date = LocalDateTime.now();

    private static VectorDataInfo vectorData;

    @BeforeEach
    void setUp() {
        vectorData = VectorDataInfo.builder()
                .id(SOME_ID)
                .name(SOME_FILE)
                .format(SHP)
                .createdAt(date.toString())
                .bucketFileUrl(SOME_URL)
                .build();
    }

    @Test
    void createObjectCreation() {
        assertAll("Head of suite test",
                () -> assertEquals(SHP, vectorData.getFormat()),
                () -> assertEquals(date.toString(), vectorData.getCreatedAt()),
                () -> assertEquals(SOME_ID, vectorData.getId()),
                () -> assertEquals(SOME_URL, vectorData.getBucketFileUrl())
        );
    }

}