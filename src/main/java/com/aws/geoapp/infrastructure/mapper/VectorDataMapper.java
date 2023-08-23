package com.aws.geoapp.infrastructure.mapper;

import com.aws.geoapp.domain.entity.VectorData;
import com.aws.geoapp.infrastructure.model.VectorDataInfo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface VectorDataMapper {
    VectorDataMapper INSTANCE = Mappers.getMapper(VectorDataMapper.class);
    VectorDataInfo toEntity(VectorData vectorData);
    VectorData toDomain(VectorDataInfo vectorDataInfo);
}
