package com.aws.geoapp.domain.entity;

import lombok.*;


@Builder
public record VectorData(String id, String createdAt, String name, String format, String bucketFileUrl) {
}
