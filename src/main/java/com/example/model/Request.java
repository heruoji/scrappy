package com.example.model;

import com.example.util.Utils;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Request {
    private String url;
    private final String callbackMethodName;
    private String method;
    private Map<String, String> headers;
    private String encoding;
    private Map<String, String> options = new HashMap<>();

    public Request(String url, String callbackKey) {
        this.url = url;
        this.callbackMethodName = callbackKey;
        this.method = "GET";
        this.headers = null;
        this.encoding = StandardCharsets.UTF_8.name();
    }

    public Request(String url, String callbackKey, String method, Map<String, String> headers, String encoding) {
        this.url = url;
        this.callbackMethodName = callbackKey;
        this.method = method;
        this.headers = headers;
        this.encoding = encoding;
    }

    public String getEncoding() {
        return encoding;
    }

    public String getUrl() {
        return url;
    }

    public String getCallbackMethodName() {
        return callbackMethodName;
    }

    public String getBaseUrl() {
        return Utils.extractBaseUrl(this.url);
    }

    public void setOption(String key, String value) {
        this.options.put(key, value);
    }

    public String getOption(String key) {
        return options.get(key);
    }

    public Map<String, String> getOptions() {
        return this.options;
    }

}
