package com.aws.geoapp.infrastructure.configuration;

import com.aws.geoapp.GeoAppApplication;
import com.aws.geoapp.domain.repository.BucketFileRepository;
import com.aws.geoapp.domain.repository.VectorDataRepository;
import com.aws.geoapp.domain.service.BucketFileService;
import com.aws.geoapp.domain.service.BucketFileServiceImpl;
import com.aws.geoapp.domain.service.VectorDataService;
import com.aws.geoapp.domain.service.VectorDataServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = GeoAppApplication.class)
public class BeanConfiguration {

    @Bean
    VectorDataService vectorDataService(final BucketFileService bucketFileService,
                                        final VectorDataRepository vectorDataRepository) {
        return new VectorDataServiceImpl(bucketFileService, vectorDataRepository);
    }

    @Bean
    BucketFileService bucketFileService(final BucketFileRepository bucketFileRepository,
                                        final AWSProperties awsProperties) {
        return new BucketFileServiceImpl(bucketFileRepository,  awsProperties);
    }

    @Bean
    public AWSProperties awsProperties() {
        return AWSProperties.builder().build();
    }

}
