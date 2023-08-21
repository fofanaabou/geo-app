package com.aws.geoapp.domain.entity;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
public record VectorData(String id, String createdAt, String name, String format, String bucketFileUrl) {
}
