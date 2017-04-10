package io.infogains.gdelt.aws.s3;

import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.event.ProgressEvent;
import com.amazonaws.event.ProgressEventType;
import com.amazonaws.event.ProgressListener;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.Upload;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by omart on 4/8/2017.
 */
public class S3Service {

    private final String BUCKET_NAME = "mcs-ds.gdelt";

    //private static final Path path = Paths.get("D:/data/in-gdelt/");
    private final AmazonS3ClientBuilder s3ClientBuilder = AmazonS3ClientBuilder.standard();

    private PutObjectRequest putObj;
    TransferManager transMgr;
    Upload upload;
    AmazonS3 s3Client;

    private ProgressListener setProgressListener(String key) {
        return new ProgressListener() {
            @Override
            public void progressChanged(ProgressEvent progressEvent) {
                long bytesSent = progressEvent.getBytesTransferred();
                System.out.println("key: " + key + " - Transferred bytes - " + bytesSent);

                System.out.println(progressEvent.getEventType());

                if(progressEvent.getEventType() == ProgressEventType.TRANSFER_COMPLETED_EVENT)
                    transMgr.shutdownNow(true);
            }
        };
    }

    public boolean pushToS3(String key, File file) throws IOException, InterruptedException {
//        DirectoryStream ds = Files.newDirectoryStream(path);
//        ds.forEach(f -> System.out.println(new File(f.toString()).length()));


        s3Client = s3ClientBuilder.withRegion(Regions.US_EAST_1)
                .withCredentials(new EnvironmentVariableCredentialsProvider())
//                .withAccelerateModeEnabled(true)
                .build();

        transMgr = TransferManagerBuilder.standard().withS3Client(s3Client).build();

        PutObjectRequest putObj = new PutObjectRequest(BUCKET_NAME, key, file);

//        s3Client.createBucket("mcs-ds.test");
//        s3Client.initiateMultipartUpload(
//                new InitiateMultipartUploadRequest("mcs-ds.gdelt", "test-file.gz"));



        putObj.setGeneralProgressListener(setProgressListener(key));

        upload = transMgr.upload(putObj);
        //upload.waitForCompletion();
        //transMgr.shutdownNow(true);

        return true;

        //s3Client.putObject(putObj);

        //GetObjectRequest getObj = new GetObjectRequest("mcs-ds.gdelt","test-file");

//        InitiateMultipartUploadRequest multipartUploadRequest =
//                new InitiateMultipartUploadRequest("mcs-ds.gdelt", "test-file");
//
//        multipartUploadRequest.
    }
}
