package com.example.spider.impl;

import com.example.spider.Spider;
import com.example.model.SpiderResponseBuilder;
import com.example.model.item.impl.AuthorItem;
import com.example.model.Request;
import com.example.model.Response;
import com.example.model.SpiderResponse;

import java.util.List;
import java.util.stream.Collectors;

public class QuoteSpider implements Spider {
    private static String name = "quote";
    private static String startUrl = "https://quotes.toscrape.com";

    public Request startRequest() {
        return new Request(startUrl, "parseMain");
    }

    public SpiderResponse parseMain(Response response) {
        List<Request> authorRequests = response.select(".author + a").stream().map(link -> new Request(link.absUrl("href"), "parseAuthor")).collect(Collectors.toList());
        List<Request> paginationRequests = response.select("li.next a").stream().map(link -> new Request(link.absUrl("href"), "parseMain")).toList();
        authorRequests.addAll(paginationRequests);

        return new SpiderResponseBuilder().requests(authorRequests).build();
    }

    public SpiderResponse parseAuthor(Response response) {
        String name = response.select("h3.author-title").text();
        String birthday = response.select(".author-born-date").text();
        String bio = response.select(".author-description").text();
        AuthorItem item = new AuthorItem();
        item.name = name;
        item.birthday = birthday;
        item.bio = bio;

        return new SpiderResponseBuilder().addItem(item).build();
    }
}
