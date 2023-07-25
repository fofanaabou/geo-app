package com.aws.geoapp.services;

import com.amazonaws.services.s3.AmazonS3;
import com.aws.geoapp.configuration.AWSProperties;
import com.aws.geoapp.models.BucketObjectInfo;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@AllArgsConstructor
public class GeoDataService implements StorageService {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(GeoDataService.class);

    private final AwsConnection awsConnection;
    private final AWSProperties awsProperties;

    private File convertFilePartToByteArray(FilePart filePart) {
        Path root = Paths.get(awsProperties.getLocalUploadPath());
        File file = new File(root.resolve(filePart.filename()).toUri());
        filePart.transferTo(file).then().block();
        return file;
    }

    @Override
    public Mono<Void> store(Mono<FilePart> filePart) {
        return filePart.doOnNext(ps -> log.info(ps.filename()))
                .flatMap(f -> {
                    uploadFile(f);
                    return Mono.empty();
                })
                .then();
    }

    private void uploadFile(FilePart f) {
        File file = convertFilePartToByteArray(f);
        AmazonS3 awsAmazon3 = awsConnection.getClient();
        awsAmazon3.putObject(awsProperties.getBucketName(), awsProperties.getS3BucketPath()+ f.filename(), file);
    }

    @Override
    public Mono<BucketObjectInfo> getBucketObject(String key, String encodingType) {
        return null;
    }

}
