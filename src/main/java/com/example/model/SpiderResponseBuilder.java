package com.example.model;

import com.example.model.item.Item;

import java.util.ArrayList;
import java.util.List;

public class SpiderResponseBuilder {

    private List<Request> requests = new ArrayList<>();
    private List<Item> items = new ArrayList<>();

    public SpiderResponseBuilder requests(List<Request> requests) {
        this.requests = requests;
        return this;
    }

    public SpiderResponseBuilder items(List<Item> items) {
        this.items = items;
        return this;
    }

    public SpiderResponseBuilder addRequest(Request request) {
        this.requests.add(request);
        return this;
    }

    public SpiderResponseBuilder addItem(Item item) {
        this.items.add(item);
        return this;
    }

    public SpiderResponse build() {
        return new SpiderResponse(requests, items);
    }
}
