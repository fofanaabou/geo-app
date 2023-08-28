package com.aws.geoapp.domain.entity;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class FormatTest {

    @Test
    void enumTest() {
        Format[] formats = {Format.DWF, Format.DXF, Format.GEOJSON, Format.SHP};
        assertAll("Head of suit test",
                () -> assertInstanceOf(Format.class, Format.DWF),
                () -> assertInstanceOf(Format.class, Format.DXF),
                () -> assertInstanceOf(Format.class, Format.SHP),
                () -> assertInstanceOf(Format.class, Format.GEOJSON),
                () -> assertEquals("shp", Format.SHP.getCode()),
                () -> assertThat(Format.values()).containsExactlyInAnyOrder(formats)
        );
    }
}