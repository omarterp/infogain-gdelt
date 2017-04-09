package io.infogains.gdelt.aws.es;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

/**
 * Created by omart_000 on 4/1/2017.
 */
public class TestMapping {

    public static void main(String[] args) throws IOException {

        Scanner scan = new Scanner(new File("index_def.json"), "UTF-8").useDelimiter("\\Z");
        //System.out.println(scan.next());

        //Scanner scan = new Scanner(new File("event_map.json"), "UTF-8").useDelimiter("\\Z");

        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElement = jsonParser.parse(scan.next());
        System.out.println(jsonElement);

        ElasticSearchService.createIndex("gdelt-2015", jsonElement.toString());
        //ElasticSearchService.mapIndex("gdelt-2015", "event", jsonElement.toString());
        //ElasticSearchService.deleteIndex("gdelt-2015");

        //ElasticSearchService.listIndices();
        //ElasticSearchService.getMapping();
    }
}
