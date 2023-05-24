package com.example.settings;

import com.example.annotation.Settings;

@Settings(name="quote")
public class QuoteSettings {
    private static String[] ITEM_PIPELINES = new String[]{
            "AuthorCsvWriterPipeline"
    };
}
