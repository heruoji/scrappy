package com.example.core.downloader.impl;

import com.example.core.downloader.DownloaderThread;
import com.example.exception.DownloaderHttpResponseException;
import com.example.model.Request;
import com.example.model.Response;
import com.example.queue.ThreadSafeQueue;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;

public class JsoupDownloader extends DownloaderThread {

    public JsoupDownloader(ThreadSafeQueue<Request> requestQueue, ThreadSafeQueue<Response> responseQueue) {
        super(requestQueue, responseQueue);
    }

    @Override
    protected void startProcess() {
    }

    @Override
    protected void finishProcess() {
    }

    @Override
    protected Response fetchPage(Request request) {
        String url = request.getUrl();
        Connection.Response response;
        try {
            response = Jsoup.connect(url).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (response.statusCode() != 200) {
            throw new DownloaderHttpResponseException(response.statusCode(), String.format("以下のURLの取得に失敗しました。'%s'", url));
        }
        return new Response(url, response.statusCode(), response.headers(), response.bodyAsBytes(), request);
    }
}
