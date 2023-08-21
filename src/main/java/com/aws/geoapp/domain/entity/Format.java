package com.aws.geoapp.domain.entity;

public enum Format {
    SHP("shp"), GEOJSON("geojson"), DXF("dxf"), DWF("dwf");


    private Format(String code) {
        this.code = code;
    }

    private final String code;

    public  String getCode() {
        return code;
    }
}
