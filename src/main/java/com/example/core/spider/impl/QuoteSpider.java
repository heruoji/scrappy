package com.example.core.spider.impl;

import com.example.core.spider.Spider;
import com.example.model.item.impl.AuthorItem;
import com.example.model.item.Item;
import com.example.model.Request;
import com.example.model.Response;
import com.example.model.SpiderResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class QuoteSpider implements Spider {
    private static String name = "quote";
    private static String startUrl = "https://quotes.toscrape.com";

    public Request startRequest() {
        return new Request(startUrl, "parseMain");
    }

    public SpiderResponse parseMain(Response response) {
        SpiderResponse result = new SpiderResponse();
        List<Request> authorRequests = response.select(".author + a").stream().map(link -> new Request(link.absUrl("href"), "parseAuthor")).collect(Collectors.toList());
        List<Request> paginationRequests = response.select("li.next a").stream().map(link -> new Request(link.absUrl("href"), "parseMain")).toList();
        authorRequests.addAll(paginationRequests);
        result.setRequests(authorRequests);
        return result;
    }

    public SpiderResponse parseAuthor(Response response) {
        SpiderResponse result = new SpiderResponse();
        String name = response.select("h3.author-title").text();
        String birthday = response.select(".author-born-date").text();
        String bio = response.select(".author-description").text();
        AuthorItem item = new AuthorItem();
        item.name = name;
        item.birthday = birthday;
        item.bio = bio;
        List<Item> items = new ArrayList<>();
        items.add(item);
        result.setItems(items);
        return result;
    }
}
