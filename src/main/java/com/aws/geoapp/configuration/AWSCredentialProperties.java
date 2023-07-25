package com.aws.geoapp.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "aws.credentials.access")
public class AWSCredentialProperties {
    private String accessKey;
    private String secretKey;
}
