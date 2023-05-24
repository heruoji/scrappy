package com.example.core.downloader.impl;

import com.example.core.downloader.DownloaderThread;
import com.example.exception.PlaywrightDownloaderException;
import com.example.model.Request;
import com.example.model.Response;
import com.example.queue.ThreadSafeQueue;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.LoadState;

public class PlaywrightDownloader extends DownloaderThread {

    private Playwright playwright;
    private Browser browser;
    private Page page;

    public PlaywrightDownloader(ThreadSafeQueue<Request> requestQueue, ThreadSafeQueue<Response> responseQueue) {
        super(requestQueue, responseQueue);
    }

    @Override
    public void startProcess() {
        initializePlaywright();
    }

    private void initializePlaywright() {
        this.playwright = Playwright.create();
        this.browser = playwright
                .chromium()
                .launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(50));
    }

    @Override
    protected void finishProcess() {
        finalizePlaywright();
    }

    protected void finalizePlaywright() {
        this.playwright.close();
    }

    @Override
    protected Response fetchPage(Request request) {
        String url = request.getUrl();
        if (page == null || page.isClosed()) {
            openNewPage();
        }
        com.microsoft.playwright.Response response = page.navigate(url);
        if (!response.ok()) {
            throw new PlaywrightDownloaderException(response.status(), "html取得エラー");
        }
        page.waitForLoadState(LoadState.LOAD);
        return new Response(url, response.status(), response.headers(), page.content().getBytes(), request);
    }

    private void openNewPage() {
        this.page = browser.newPage();
    }
}
