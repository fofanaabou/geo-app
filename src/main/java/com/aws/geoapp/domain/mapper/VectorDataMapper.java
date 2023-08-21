package com.aws.geoapp.domain.mapper;

import com.aws.geoapp.domain.entity.VectorData;
import com.aws.geoapp.infrastructure.model.VectorDataInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface VectorDataMapper {
    VectorDataMapper INSTANCE = Mappers.getMapper(VectorDataMapper.class);
    @Mapping(target = "id", ignore = true)
    VectorDataInfo toEntity(VectorData vectorData);
    VectorData toDomain(VectorDataInfo vectorDataInfo);
}
