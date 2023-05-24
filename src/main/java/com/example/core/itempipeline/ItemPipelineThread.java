package com.example.core.itempipeline;

import com.example.model.item.Item;
import com.example.queue.ThreadSafeQueue;

import java.util.List;

public class ItemPipelineThread extends Thread {

    private ThreadSafeQueue<Item> itemQueue;
    private List<ItemPipeline> itemPipelines;

    public ItemPipelineThread(ThreadSafeQueue<Item> itemQueue, List<ItemPipeline> itemPipelines) {
        this.itemQueue = itemQueue;
        this.itemPipelines = itemPipelines;
    }

    @Override
    public void run() {
        while (true) {
            Item item = itemQueue.remove();
            if (item == null) {
                break;
            }
            itemPipelines.forEach(itemPipeline -> itemPipeline.processItem(item));
        }
    }
}
