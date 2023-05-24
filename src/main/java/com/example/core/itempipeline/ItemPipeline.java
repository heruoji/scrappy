package com.example.core.itempipeline;

import com.example.model.item.Item;

public interface ItemPipeline {

    default void openSpider() {
    }

    default void closeSpider() {
    }

    Item processItem(Item item);
}
