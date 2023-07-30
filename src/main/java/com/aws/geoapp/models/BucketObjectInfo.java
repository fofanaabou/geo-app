package com.aws.geoapp.models;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BucketObjectInfo {
    private String objectUrl;
    private String key;
}
