package io.infogains.gdelt.web;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by omart_000 on 2/25/2017.
 */
public class HarvestGdeltV1 {

    public static List<String> getFileList(String url) throws IOException {
        List<String> fileList = new ArrayList<>();

        Document doc = Scraper.connect(url);

        String[] bodySplit = doc.body().toString().split("\n");
        String bodyElement = bodySplit[1];
        String[] tokens = bodyElement.split("\\d+\\s");

        for(String token : tokens) {
            //System.out.println(token);
            if(downloadFileCheck(token))
                fileList.add(token.trim().toLowerCase());
            //System.out.println(downloadFileCheck(token));
        }

        return fileList;
    }

    // Check file to ensure it is less than 20150219
    public static boolean downloadFileCheck(String fileName) {
        boolean downloadFlag = false;
        String[] tokens = fileName.split("\\.");
        String firstToken = tokens[0];
        try {
            if(Integer.parseInt(firstToken) < 20150219)
                downloadFlag = true;
        } catch(NumberFormatException nfe) {
            downloadFlag = false;
        }

        return downloadFlag;
    }

    public static void main(String[] args) throws IOException {
        Map<String, Object> fileDownloads = new HashMap<>();
        List<String> fileList = new ArrayList<>();

        //Scraper scraping = new Scraper("http://data.gdeltproject.org/events/index.html");
        try {
            final Document doc = Scraper.connect("http://data.gdeltproject.org/events/index.html");
            for(Element result : doc.select("li a")) {
                final String title = result.text().trim().toLowerCase();
                final String url = result.attr("abs:href");

                //System.out.println(title + "->" + url);
                fileDownloads.put(title, url);
                if(title.equals("filesizes"))
                    fileList = getFileList(url);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(String title : fileList) {
            //System.out.println(title);
            System.out.println(fileDownloads.get(title));

            File myFile = new File(title);

            CloseableHttpClient client = HttpClients.createDefault();
            try (CloseableHttpResponse response = client.execute(new HttpGet(fileDownloads.get(title).toString()))) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    try (FileOutputStream outstream = new FileOutputStream(myFile)) {
                        entity.writeTo(outstream);
                    }
                }
            }
        }

//        System.out.println(fileDownloads);
//
//        File myFile = new File("mystuff.bin");
//
//        CloseableHttpClient client = HttpClients.createDefault();
//        try (CloseableHttpResponse response = client.execute(new HttpGet("http://data.gdeltproject.org/events/20170226.export.CSV.zip"))) {
//            HttpEntity entity = response.getEntity();
//            if (entity != null) {
//                try (FileOutputStream outstream = new FileOutputStream(myFile)) {
//                    entity.writeTo(outstream);
//                }
//            }
//        }

    }
}
