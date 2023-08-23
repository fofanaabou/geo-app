package com.aws.geoapp.infrastructure.repository;

import com.aws.geoapp.domain.entity.VectorData;
import com.aws.geoapp.infrastructure.mapper.VectorDataMapper;
import com.aws.geoapp.domain.repository.VectorDataRepository;
import com.aws.geoapp.infrastructure.model.VectorDataInfo;
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
        VectorDataInfo vectorDataInfo = vectorDataMapper.toEntity(vectorData);
        return vectorDataMapper.toDomain(dynamoDBRepository.save(vectorDataInfo));
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
