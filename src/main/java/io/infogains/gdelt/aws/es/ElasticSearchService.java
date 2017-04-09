package io.infogains.gdelt.aws.es;

import com.amazonaws.*;
import com.amazonaws.auth.AWS4Signer;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.http.*;
import com.amazonaws.regions.Regions;
import com.amazonaws.transform.Unmarshaller;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.infogains.gdelt.aws.common.Util;
import org.w3c.dom.Node;

import java.io.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by omart_000 on 3/26/2017.
 *
 * Handle Signed ElasticSearchService Requests
 */
public class ElasticSearchService {

    private static final String SERVICE_NAME = "es";
    private static final String SERVICE_URL = "https://search-mcsds-gdelt-w34iggu3cr4h72hsdmaymiixhy.us-east-1.es.amazonaws.com/";

    ElasticSearchService() throws IOException {

//        //Instantiate the request
//        Request<Void> request = new DefaultRequest<Void>("es"); //?!?
//        request.setHttpMethod(HttpMethodName.POST);
//        request.addHeader("Content-Type", "application/x-ndjson");
//        request.setContent(new ByteArrayInputStream(testString.getBytes()));
//        request.setEndpoint(URI.create("https://search-mcsds-gdelt-w34iggu3cr4h72hsdmaymiixhy.us-east-1.es.amazonaws.com/_bulk"));




//                .execute(request, null, null, new ExecutionContext(true));
//                .requestExecutionBuilder()
//                .executionContext() //?!?
//                .request(request)
//                .errorResponseHandler(new SimpleAwsErrorHandler())
//                .execute(new SimpleResponseHandler<String>());

        //HttpResponse httpResp = resp.getHttpResponse();
//        Gson gson = new Gson();
//        Reader reader = new InputStreamReader(resp.getHttpResponse().getContent());
//        System.out.println(reader.ready());

    }

    public static boolean bulkLoad(StringBuffer batch) {
        String endpoint = SERVICE_URL + "_bulk";
        return Util.handleAWSRequest(SERVICE_NAME, batch.toString(), endpoint, HttpMethodName.POST);
    }

    public static boolean createIndex(String indexName, String mappingDef) {
        String endpoint = SERVICE_URL + indexName;// + "/_mapping/" + type;
        return Util.handleAWSRequest(SERVICE_NAME, mappingDef, endpoint, HttpMethodName.PUT);
    }

    public static boolean mapIndex(String indexName, String type, String mappingDef) {
        String endpoint = SERVICE_URL + indexName+ "/_mapping/" + type;
        return Util.handleAWSRequest(SERVICE_NAME, mappingDef, endpoint, HttpMethodName.PUT);
    }

    public static boolean listIndices() {
        String endpoint = SERVICE_URL + "/_cat/indices?v";
        return Util.handleAWSRequest(SERVICE_NAME, "", endpoint, HttpMethodName.GET);
    }

    public static boolean getMapping() {
        String endpoint = SERVICE_URL + "/_mapping";
        return Util.handleAWSRequest(SERVICE_NAME, "", endpoint, HttpMethodName.GET);
    }

    public static boolean deleteIndex(String indexName) {
        String endpoint = SERVICE_URL + indexName;
        return Util.handleAWSRequest(SERVICE_NAME, "", endpoint, HttpMethodName.DELETE);
    }

    public static JsonObject getDocumentHeader(String index, String type, String id) {
        JsonObject innerDoc = new JsonObject();
        innerDoc.addProperty("_index", index);
        innerDoc.addProperty("_type", type);
        innerDoc.addProperty("_id", id);

        JsonObject docHeader = new JsonObject();
        docHeader.add("index", innerDoc);

        return docHeader;
    }

    public static void main(String[] args) {
        String testString = "{ \"index\" : { \"_index\" : \"gdelt-2014\", \"_type\" : \"event\", \"_id\" : \"297951908\" } }\n"+
                "{\"geid\":\"297951908\",\"eDt\":\"2014-05-21\",\"eDayYr\":140,\"eDayWk\":2,\"eDayMth\":20,\"eMth\":5,\"eYr\":2014,\"eMthYr\":201405,\"fractionDate\":2014.3836,\"a1Code\":\"USAJUD\",\"a1Name\":\"UNITED STATES\",\"a1Country\":\"USA\",\"a1Group\":\"\",\"a1Ethnicity\":\"\",\"a1Religion1\":\"\",\"a1Religion2\":\"\",\"a1Type1\":\"JUD\",\"a1Type2\":\"\",\"a1Type3\":\"\",\"a2Code\":\"ECU\",\"a2Name\":\"ECUADOR\",\"a2Country\":\"ECU\",\"a2Group\":\"\",\"a2Ethnicity\":\"\",\"a2Religion1\":\"\",\"a2Religion2\":\"\",\"a2Type1\":\"\",\"a2Type2\":\"\",\"a2Type3\":\"\",\"isRootEvent\":1,\"eventCode\":\"0874\",\"eventBaseCode\":\"087\",\"eventRootCode\":\"08\",\"quadClass\":\"2\",\"goldsteinScale\":10.0,\"numMentions\":1.0,\"numSources\":1.0,\"numArticles\":1.0,\"avgTone\":1.53417015341702,\"a1GeoType\":\"3\",\"a1GeoName\":\"Burlington, Massachusetts, United States\",\"a1GeoCountry\":\"US\",\"a1GeoAdm\":\"USMA\",\"a1Lat\":42.5048,\"a1Long\":-71.1956,\"a1GeoFeature\":\"612150\",\"a2GeoType\":\"3\",\"a2GeoName\":\"Boston, Massachusetts, United States\",\"a2GeoCountry\":\"US\",\"a2GeoAdm\":\"USMA\",\"a2Lat\":42.3584,\"a2Long\":-71.0598,\"a2GeoFeature\":\"617565\",\"actionGeoType\":\"3\",\"actionGeoName\":\"Boston, Massachusetts, United States\",\"actionCountry\":\"US\",\"actionGeoAdm\":\"USMA\",\"actionLat\":42.3584,\"actionLong\":-71.0598,\"actionGeoFeature\":\"617565\",\"dateAdded\":\"May 19, 2014 12:00:00 AM\",\"sourceUrl\":\"http://www.gopusa.com/freshink/2014/05/19/the-banana-republic-of-massachusetts/\"}\n";

        ElasticSearchService.bulkLoad(new StringBuffer(testString));
    }
}
