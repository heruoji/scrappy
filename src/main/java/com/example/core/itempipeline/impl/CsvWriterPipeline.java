package com.example.core.itempipeline.impl;

import com.example.model.item.impl.CrawlListItem;
import com.example.model.item.Item;
import com.example.core.itempipeline.ItemPipeline;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class CsvWriterPipeline implements ItemPipeline {

    private CSVPrinter csvPrinter;

    @Override
    public void openSpider() {
        try {
            Writer writer = new FileWriter("crawler.list");
            this.csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("path1", "path2", "path3", "url"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void closeSpider() {
        try {
            this.csvPrinter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Item processItem(Item item) {
        try {
            CrawlListItem crawlListItem = (CrawlListItem) item;
            csvPrinter.printRecord(crawlListItem.pathStr1, crawlListItem.pathStr2, crawlListItem.pathStr3, crawlListItem.url);
            csvPrinter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return item;
    }
}
