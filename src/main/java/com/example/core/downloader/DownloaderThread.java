package com.example.core.downloader;

import com.example.model.Request;
import com.example.model.Response;
import com.example.queue.ThreadSafeQueue;

public abstract class DownloaderThread extends Thread {

    private final ThreadSafeQueue<Request> requestQueue;
    private final ThreadSafeQueue<Response> responseQueue;

    public DownloaderThread(ThreadSafeQueue<Request> requestQueue, ThreadSafeQueue<Response> responseQueue) {
        this.requestQueue = requestQueue;
        this.responseQueue = responseQueue;
    }

    @Override
    public void run() {
        startProcess();
        while (true) {
            Request request = requestQueue.remove();
            if (request == null) {
                responseQueue.terminate();
                System.out.println("No more request in requestQueue. responseQueue is terminating...");
                break;
            }
            System.out.println("fetching page: " + request.getUrl());
            try {
                Response response = fetchPage(request);
                responseQueue.add(response);
            } catch (Throwable e) {
                e.printStackTrace();
                if (requestQueue.isEmpty()) {
                    responseQueue.terminate();
                }
            }
        }
        finishProcess();
    }

    protected abstract void startProcess();

    protected abstract void finishProcess();

    protected abstract Response fetchPage(Request request);

}
