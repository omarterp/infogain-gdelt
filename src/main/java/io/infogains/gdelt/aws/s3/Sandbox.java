package io.infogains.gdelt.aws.s3;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.InitiateMultipartUploadRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

/**
 * Created by omart_000 on 4/9/2017.
 */
public class Sandbox {

    private static Path path = Paths.get("files");
    private static AmazonS3ClientBuilder s3ClientBuilder = AmazonS3ClientBuilder.standard();




    public static void main(String[] args) throws IOException {
        DirectoryStream ds = Files.newDirectoryStream(path);
        ds.forEach(f -> System.out.println(new File(f.toString()).length()));

        AmazonS3 s3Client = s3ClientBuilder.withRegion(Regions.US_EAST_1)
                .withCredentials(new EnvironmentVariableCredentialsProvider())
//                .withAccelerateModeEnabled(true)
                .build();

        s3Client.createBucket("mcs-ds.test");
//        s3Client.initiateMultipartUpload(
//                new InitiateMultipartUploadRequest("mcs-ds.gdelt", "test-file.gz"));

        PutObjectRequest putObj = new PutObjectRequest("mcs-ds.gdelt",
                "test-file.gz", new File("files/2000.zip"));

        //s3Client.putObject(putObj);

        //GetObjectRequest getObj = new GetObjectRequest("mcs-ds.gdelt","test-file");

//        InitiateMultipartUploadRequest multipartUploadRequest =
//                new InitiateMultipartUploadRequest("mcs-ds.gdelt", "test-file");
//
//        multipartUploadRequest.

    }

}
