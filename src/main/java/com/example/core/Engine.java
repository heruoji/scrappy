package com.example.core;

import com.example.core.downloader.DownloaderThread;
import com.example.model.item.Item;
import com.example.core.itempipeline.ItemPipeline;
import com.example.core.itempipeline.ItemPipelineThread;
import com.example.model.Request;
import com.example.model.Response;
import com.example.queue.ThreadSafeQueue;
import com.example.core.scraper.ScraperThread;
import com.example.core.spider.Spider;
import com.example.util.args.Args;
import com.example.util.args.ArgsException;

import javax.annotation.concurrent.ThreadSafe;
import java.util.List;

import static com.example.core.Container.*;

public class Engine extends Thread {

    private static Args arg;

    private static ThreadSafeQueue<Request> requestQueue;
    private static ThreadSafeQueue<Response> responseQueue;
    private static ThreadSafeQueue<Item> itemQueue;

    private static Spider spider;
    private static List<ItemPipeline> itemPipelines;

    private static DownloaderThread downloaderThread;
    private static ScraperThread scraperThread;
    private static ItemPipelineThread itemPipelineThread;

    public static void main(String[] args) throws InterruptedException, ArgsException{
        try {
            initialize(args);
            preprocess();
            startThreads();
            postprocess();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void initialize(String[] args) throws ArgsException {
        arg = new Args("n*,p,s*", args);
        requestQueue = new ThreadSafeQueue();
        responseQueue = new ThreadSafeQueue();
        itemQueue = new ThreadSafeQueue<>();
        spider = buildSpider(arg.getString('n'));
        itemPipelines = buildItemPipelines(arg.getString('s'));
        downloaderThread = buildDownloader(arg.getBoolean('p'), requestQueue, responseQueue);
        scraperThread = new ScraperThread(requestQueue, responseQueue, itemQueue, spider);
        itemPipelineThread = new ItemPipelineThread(itemQueue, itemPipelines);
    }

    private static void preprocess() {
        itemPipelines.forEach(ItemPipeline::openSpider);
        requestQueue.add(spider.startRequest());
    }

    private static void startThreads() throws InterruptedException {
        downloaderThread.start();
        scraperThread.start();
        itemPipelineThread.start();
        downloaderThread.join();
        scraperThread.join();
        itemPipelineThread.join();
    }

    private static void postprocess() {
        itemPipelines.forEach(ItemPipeline::closeSpider);
    }

}
