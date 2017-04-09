package io.infogains.gdelt.web;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Created by omart_000 on 2/25/2017.
 */
public class Scraper {

    //We need a real browser user agent or Google will block our request with a 403 - Forbidden
    public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36";

//    private String url;
//
//    public Scraper(String url) {
//        this.url = url;
//    }
//
//    public String getUrl() {
//        return url;
//    }
//
//    public Scraper setUrl(String url) {
//        this.url = url;
//        return this;
//    }

    public static Document connect(String url) throws IOException {
        Document doc = null;

//        if(this == null)
//            throw new RuntimeException("Please initialize");

        return doc = Jsoup.connect(url).userAgent(USER_AGENT).get();
    }
}
