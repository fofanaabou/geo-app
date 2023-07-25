package com.aws.geoapp.models;

import lombok.*;

@Data
public class BucketObjectInfo {
    private String key;
    private String size;
    private String lastModified;
}
