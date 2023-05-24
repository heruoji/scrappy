package com.example.core.itempipeline.impl;

import com.example.model.item.impl.AuthorItem;
import com.example.model.item.Item;
import com.example.core.itempipeline.ItemPipeline;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class AuthorCsvWriterPipeline implements ItemPipeline {

    private CSVPrinter csvPrinter;

    @Override
    public void openSpider() {
        try {
            Writer writer = new FileWriter("authors.csv");
            this.csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("name", "birthday", "bio"));
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
            AuthorItem authorItem = (AuthorItem) item;
            csvPrinter.printRecord(authorItem.name, authorItem.birthday, authorItem.bio);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return item;
    }
}
