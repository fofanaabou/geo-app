package com.aws.geoapp.domain.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class VectorDataTest {

    public static final String SHP = "shp";
    public static final String SOME_URL = "someUrl";
    public static final String SOME_ID = "someId";
    private static VectorData vectorData;
    private final LocalDateTime date = LocalDateTime.now();

    @BeforeEach
    void setUp() {
        vectorData = VectorData.builder()
                .format(SHP)
                .bucketFileUrl(SOME_URL)
                .id(SOME_ID)
                .createdAt(date.toString())
                .build();
    }

    @Test
    void createVectorData() {
        assertAll("Head of suite test",
                () -> assertEquals(SHP, vectorData.format()),
                () -> assertEquals(date.toString(), vectorData.createdAt()),
                () -> assertEquals(SOME_ID, vectorData.id()),
                () -> assertEquals(SOME_URL, vectorData.bucketFileUrl())
                );
    }

}