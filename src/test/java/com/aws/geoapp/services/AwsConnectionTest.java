package com.aws.geoapp.services;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AwsConnectionTest {

    private static final List<String> bucketNames = List.of( "dev-afo-bucket", "geodata20");

    @Autowired
    private  AwsConnection awsConnection;

    private  AmazonS3 awsClient;


    @Test
    @DisplayName("Test connection to aws")
    void getClient() {
        awsClient = awsConnection.getClient();
        List<String> commons = getListOfBucketNames().stream()
                .distinct()
                .filter(bucketNames::contains)
                .toList();

       assertAll("Test connection to AWS",
               () -> assertNotNull(awsClient.listBuckets()),
               () ->  assertEquals(bucketNames.get(0), commons.get(0)),
               () ->  assertEquals(bucketNames.get(1), commons.get(1)));
    }

    private List<String> getListOfBucketNames() {
        return awsClient.listBuckets().stream().map(Bucket::getName).sorted().toList();
    }

}