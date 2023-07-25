package com.aws.geoapp.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "aws")
public class AWSProperties {
    private String accessKey;
    private String secretKey;
    private String bucketName;
    private String region;
    private String endpoint;
    private String s3BucketPath;
    private String s3BucketUrl;
    private String s3BucketName;
    private String s3BucketRegion;
    private String s3BucketEndpoint;
    private String s3BucketPathUrl;
    private String s3BucketUrlName;
    private String s3BucketUrlRegion;
    private String s3BucketUrlEndpoint;
    private String s3BucketUrlPath;
    private String s3BucketUrlPathUrl;
    private String s3BucketUrlPathName;
    private String s3BucketUrlPathRegion;
    private String s3BucketUrlPathEndpoint;
    private String s3BucketUrlPathPath;
    private String s3BucketUrlPathPathUrl;
    private String s3BucketUrlPathPathName;
    private String s3BucketUrlPathPathRegion;
    private String s3BucketUrlPathPathEndpoint;
    private String s3BucketUrlPathPathPath;
    private String s3BucketUrlPathPathPathUrl;
    private String localUploadPath;
}
