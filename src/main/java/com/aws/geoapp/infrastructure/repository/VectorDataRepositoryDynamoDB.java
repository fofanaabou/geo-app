package com.aws.geoapp.infrastructure.repository;

import com.aws.geoapp.domain.entity.VectorData;
import com.aws.geoapp.domain.mapper.VectorDataMapper;
import com.aws.geoapp.domain.repository.VectorDataRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class VectorDataRepositoryDynamoDB implements VectorDataRepository {

    private final DynamoDBRepository dynamoDBRepository;
    private static final VectorDataMapper vectorDataMapper = VectorDataMapper.INSTANCE;
    @Override
    public VectorData save(VectorData vectorData) {
        return vectorDataMapper.toDomain(dynamoDBRepository.save(vectorDataMapper.toEntity(vectorData)));
    }

    @Override
    public Optional<VectorData> findById(String id) {
        return dynamoDBRepository.findById(id).map(vectorDataMapper::toDomain);
    }

    @Override
    public Iterable<VectorData> findAll() {
        List<VectorData> vectors = new ArrayList<>();
        dynamoDBRepository.findAll().forEach(v-> vectors.add(vectorDataMapper.toDomain(v)));
        return vectors;
    }
}
