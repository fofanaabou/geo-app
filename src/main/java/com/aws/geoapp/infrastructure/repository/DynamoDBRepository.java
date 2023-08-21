package com.aws.geoapp.infrastructure.repository;

import com.aws.geoapp.infrastructure.model.VectorDataInfo;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface DynamoDBRepository extends CrudRepository<VectorDataInfo, String> {
}
