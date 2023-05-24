package com.example.settings;

import com.example.annotation.Settings;

@Settings(name="rikunabiNext")
public class RikunaviNextSettings{
    private static String[] ITEM_PIPELINES = new String[]{
            "CsvWriterPipeline"
    };
}
