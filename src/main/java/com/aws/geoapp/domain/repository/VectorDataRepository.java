package com.aws.geoapp.domain.repository;

import com.aws.geoapp.domain.entity.VectorData;

import java.util.Optional;

public interface VectorDataRepository {
    VectorData save(VectorData vectorData);
    Optional<VectorData> findById(String id);
    Iterable<VectorData> findAll();
}
