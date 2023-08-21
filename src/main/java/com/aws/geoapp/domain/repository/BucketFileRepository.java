package com.aws.geoapp.domain.repository;

import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.PutObjectResult;
import org.springframework.http.codec.multipart.FilePart;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;

public interface BucketFileRepository {
    Optional<PutObjectResult> store(FilePart filePart) throws IOException;
    ObjectListing getBucketObjects(String bucketName, String path);
    URL getUrl(String bucketName, String key);
}
