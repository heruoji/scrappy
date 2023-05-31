package com.example.spider.impl;

import com.example.spider.Spider;
import com.example.model.item.impl.CrawlListItem;
import com.example.model.item.Item;
import com.example.model.Request;
import com.example.model.Response;
import com.example.model.SpiderResponse;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class RikunabiNextSpider implements Spider {
    private static String name = "rikunabiNext";
    private String startUrl = "https://next.rikunabi.com";

    @Override
    public Request startRequest() {
        return new Request(startUrl, "parse1");
    }

    public SpiderResponse parse1(Response response) {

        List<Request> areaRequests = new ArrayList<>();

        Elements elements = response.select(".mtp-FooterSearch__area--1T4mq .mtp-FooterSearch__area__contents--2MFoy");
        for (Element element : elements) {
            String area1 = element.select(".mtp-FooterSearch__area__mainLink--mMtJt").text();
            Elements elements2 = element.select("li.mtp-footer__linkList");
            for (Element element2 : elements2) {
                String area2 = element2.select("a").first().text();
                String areaUrl = element2.select("a").first().absUrl("href");
                Request request = new Request(areaUrl, "parse2");
                request.setOption("area1", area1);
                request.setOption("area2", area2);
                areaRequests.add(request);
            }
        }

        SpiderResponse res = new SpiderResponse();
        res.setRequests(areaRequests);
        return res;
    }

    public SpiderResponse parse2(Response response) {

        List<Item> items = new ArrayList<>();

        Elements elements = response.select(".rnn-group:nth-child(1) .rnn-refineSearchlist li.rnn-refineSearchlist__item a");
        for (Element element : elements) {
            String url = element.absUrl("href");
            String area3 = element.text();
            CrawlListItem item = new CrawlListItem();
            item.url = url;
            item.pathStr1 = response.getOption("area1");
            item.pathStr2 = response.getOption("area2");
            item.pathStr3 = area3;
            items.add(item);
        }

        SpiderResponse res = new SpiderResponse();
        res.setItems(items);
        return res;
    }

}
