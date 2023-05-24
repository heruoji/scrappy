package com.example.core.scraper;

import com.example.model.item.Item;
import com.example.model.Request;
import com.example.model.Response;
import com.example.model.SpiderResponse;
import com.example.queue.ThreadSafeQueue;
import com.example.core.spider.Spider;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ScraperThread extends Thread {
    private ThreadSafeQueue<Request> requestQueue;
    private ThreadSafeQueue<Response> responseQueue;
    private ThreadSafeQueue<Item> itemQueue;
    private Spider spider;
    private static Set<String> activeUrl = new HashSet<>();

    public ScraperThread(
            ThreadSafeQueue<Request> requestQueue,
            ThreadSafeQueue<Response> responseQueue,
            ThreadSafeQueue<Item> itemPipelineQueue,
            Spider spider) {
        this.requestQueue = requestQueue;
        this.responseQueue = responseQueue;
        this.itemQueue = itemPipelineQueue;
        this.spider = spider;
    }

    @Override
    public void run() {
        while (true) {
            Response response = responseQueue.remove();
            if (response == null) {
                requestQueue.terminate();
                System.out.println("Request Queue is terminating...");
                itemQueue.terminate();
                System.out.println("Item Queue is terminating...");
                break;
            }
            SpiderResponse spiderResponse = spider.parse(response);
            if (spiderResponse.hasRequests()) {
                List<Request> newUrlRequests = spiderResponse.getRequests();
                newUrlRequests.forEach(requestQueue::add);
                newUrlRequests.forEach(request -> activeUrl.add(request.getUrl()));
            }

            if (spiderResponse.hasItems()) {
               spiderResponse.getItems().forEach(itemQueue::add);
            }

            activeUrl.remove(response.getRequestUrl());
            if (responseQueue.isEmpty() && requestQueue.isEmpty() && activeUrl.isEmpty() && !spiderResponse.hasRequests()) {
                requestQueue.terminate();
                System.out.println("Request Queue is terminating...");
                itemQueue.terminate();
                System.out.println("Item Queue is terminating...");
                break;
            }
        }
    }
}
