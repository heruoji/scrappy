package com.example.model;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.Map;

public class Response {
    private String url;
    private int status;
    private Map<String, String> headers;
    private byte[] body;
    private Request request;

    public Response(String url, int status, Map<String, String> headers, byte[] body, Request request) {
        this.url = url;
        this.status = status;
        this.headers = headers;
        this.body = body;
        this.request = request;
    }

    public byte[] getBody() {
        return body;
    }

    public String getEncoding() {
        return this.request.getEncoding();
    }

    public String getCallbackKey() {
        return this.request.getCallbackMethodName();
    }

    public String getRequestUrl() {
        return this.request.getUrl();
    }

    public String getBaseUrl() {
        return this.request.getBaseUrl();
    }

    public Elements select(String query) {
        Document doc = Jsoup.parse(new String(getBody()), request.getBaseUrl());
        return doc.select(query);
    }

    public String getOption(String key) {
        return this.request.getOption(key);
    }
}
