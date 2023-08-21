package com.aws.geoapp.domain.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BucketFile {
    private String name;
    private String key;
    private String versionId;
    private String presignedUrl;
}
