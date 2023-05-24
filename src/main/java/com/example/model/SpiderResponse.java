package com.example.model;

import com.example.model.item.Item;

import java.util.List;

public class SpiderResponse {

    private List<Request> requests;
    private List<Item> items;

    public void setRequests(List<Request> requests) {
        this.requests = requests;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public List<Request> getRequests() {
        return requests;
    }

    public List<Item> getItems() {
        return items;
    }

    public boolean hasRequests() {
        return requests != null && requests.size() > 0;
    }

    public boolean hasItems() {
        return items != null && items.size() > 0;
    }

}
