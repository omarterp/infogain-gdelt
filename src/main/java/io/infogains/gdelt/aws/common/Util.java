package io.infogains.gdelt.aws.common;

import com.amazonaws.*;
import com.amazonaws.auth.AWS4Signer;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.http.AmazonHttpClient;
import com.amazonaws.http.DefaultErrorResponseHandler;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.regions.Regions;
import com.amazonaws.transform.Unmarshaller;
import org.w3c.dom.Node;

import java.io.ByteArrayInputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by omart on 4/8/2017.
 */
public class Util {

    public static AWS4Signer getSigner(String serviceName) {
        AWS4Signer signer = new AWS4Signer(); //?!?
        signer.setRegionName(Regions.US_EAST_1.getName());
        signer.setServiceName(serviceName);

        return signer;
    }

    public static AWSCredentials getAWSCreds() {
        EnvironmentVariableCredentialsProvider credentialsProvider = new EnvironmentVariableCredentialsProvider();
        return credentialsProvider.getCredentials();
    }

    public static boolean handleAWSRequest(String serviceName, String content, String endpoint, HttpMethodName httpMethodName) {
        final String CONTENT_TYPE = "application/x-ndjson";

        //Instantiate the request
        Request<Void> request = new DefaultRequest<>(serviceName); //?!?
        request.setHttpMethod(httpMethodName);
        request.addHeader("Content-Type", CONTENT_TYPE);

        if(httpMethodName != HttpMethodName.GET) {
            request.setContent(new ByteArrayInputStream(content.getBytes()));
        }

        request.setEndpoint(URI.create(endpoint));

        // Sign Request
        Util.getSigner(serviceName).sign(request, Util.getAWSCreds());

        // Breakout into doPost
        List<Unmarshaller<AmazonServiceException, Node>> respErrors = new ArrayList<>();

        AmazonHttpClient awsHttpClient = new AmazonHttpClient(new ClientConfiguration());

        Response resp = awsHttpClient.requestExecutionBuilder()
                .errorResponseHandler(new DefaultErrorResponseHandler(respErrors))
                .request(request)
                .execute();

        return true;
    }
}
