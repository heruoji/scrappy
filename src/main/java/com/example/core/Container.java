package com.example.core;

import com.example.annotation.Settings;
import com.example.core.downloader.DownloaderThread;
import com.example.core.downloader.impl.JsoupDownloader;
import com.example.core.downloader.impl.PlaywrightDownloader;
import com.example.exception.ContainerException;
import com.example.core.itempipeline.ItemPipeline;
import com.example.model.Request;
import com.example.model.Response;
import com.example.queue.ThreadSafeQueue;
import com.example.core.spider.Spider;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.example.exception.ContainerException.ErrorCode.*;
import static com.example.util.Utils.convertFileToClazz;
import static com.example.util.Utils.getAllClassFiles;

public class Container {


//    Build Spider
    public static Spider buildSpider(String name) {
        List<File> list = getAllClassFiles();
        for (File file : list) {
            Class<?> clazz = convertFileToClazz(file);
            if (clazz.isInterface()) {
                continue;
            }
            if (isSpiderClazzHasName(clazz, name)) {
                return instantiateSpider(clazz);
            }
        }
        throw new ContainerException(SPIDER_FAILED_BUILD, String.format("Spiderの作成に失敗しました。名前が正しいか確認してください。'%s'", name));
    }

    private static boolean isSpiderClazzHasName(Class<?> clazz, String name) {
        if (!isSpiderClazz(clazz)) {
            return false;
        }
        String spiderName = getSpiderName(clazz);
        return spiderName.equals(name);
    }

    private static boolean isSpiderClazz(Class<?> clazz) {
        return Spider.class.isAssignableFrom(clazz);
    }

    private static String getSpiderName(Class<?> clazz) {
        Field field = null;
        try {
            field = clazz.getDeclaredField("name");
        } catch (NoSuchFieldException e) {
            throw new ContainerException(SPIDER_NAME_NOT_FOUND, String.format("次のクラスのSpiderにnameフィールドがありません'%s'", clazz.getSimpleName()));
        }
        field.setAccessible(true);
        try {
            return (String) field.get(null);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static Spider instantiateSpider(Class<?> clazz) {
        try {
            return (Spider) clazz.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new ContainerException(SPIDER_FAILED_INSTANTIATION, String.format("次のSpiderのインスタンス化に失敗しました'%s'", clazz.getSimpleName()));
        }
    }


//    Build Downloader
    public static DownloaderThread buildDownloader(boolean usePlaywright, ThreadSafeQueue<Request> requestQueue, ThreadSafeQueue<Response> responseQueue) {
        if (usePlaywright) {
            return new PlaywrightDownloader(requestQueue, responseQueue);
        } else {
            return new JsoupDownloader(requestQueue, responseQueue);
        }
    }


//    Build ItemPipelines
    public static List<ItemPipeline> buildItemPipelines(String settingsName) {
        Class<?> clazz = getSettingsClazz(settingsName);
        String[] itemPipelineClassNames = getItemPipelinesClassNamesFromSettings(clazz);
        return buildItemPipelinesFromClassNames(itemPipelineClassNames);
    }

    private static String[] getItemPipelinesClassNamesFromSettings(Class<?> clazz) {
        Field field;
        try {
            field = clazz.getDeclaredField("ITEM_PIPELINES");
        } catch (NoSuchFieldException e) {
            return new String[]{};
        }
        field.setAccessible(true);
        String[] itemPipelineNames;
        try {
            itemPipelineNames = (String[]) field.get(null);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        return itemPipelineNames;
    }

    private static List<ItemPipeline> buildItemPipelinesFromClassNames(String[] classNames) {
        if (classNames == null || classNames.length == 0) {
            return Collections.emptyList();
        }
        List<ItemPipeline> itemPipelines = new ArrayList<>();
        for (String itemPipelineName : classNames) {
            ItemPipeline itemPipeline = buildItemPipeline(itemPipelineName);
            itemPipelines.add(itemPipeline);
        }
        return itemPipelines;
    }

    private static ItemPipeline buildItemPipeline(String className) {
        List<File> allClassFiles = getAllClassFiles();
        for (File file : allClassFiles) {
            Class<?> clazz = null;
            clazz = convertFileToClazz(file);
            if (clazz.isInterface()) {
                continue;
            }
            if (isItemPipeline(clazz)) {
                if (clazz.getSimpleName().equals(className)) {
                    return instantiateItemPipeline(clazz);
                }
            }
        }
        throw new ContainerException(ITEM_PIPELINE_MISSING, String.format("次のItemPipelineクラスが見つかりません。'%s'", className));
    }

    private static ItemPipeline instantiateItemPipeline(Class<?> clazz) {
        try {
            return (ItemPipeline) clazz.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new ContainerException(ITEM_PIPELINE_FAILED_INSTANTIATION, String.format("次のItemPipelineクラスのインスタンス化に失敗しました。'%s'", clazz.getSimpleName()));
        }
    }

    private static boolean isItemPipeline(Class<?> clazz) {
        return ItemPipeline.class.isAssignableFrom(clazz);
    }


    private static Class<?> getSettingsClazz(String name) {
        List<File> allClassFiles = getAllClassFiles();
        for (File file : allClassFiles) {
            Class<?> clazz = null;
            clazz = convertFileToClazz(file);
            if (clazz.isInterface()) {
                continue;
            }
            if (isSettingClazzHasName(clazz, name)) {
                return clazz;
            }
        }
        throw new ContainerException(SETTINGS_MISSING, String.format("次の設定ファイルが見つかりません。'%s'", name));
    }

    private static boolean isSettingClazzHasName(Class<?> clazz, String name) {
        if (!hasSettingsAnnotation(clazz)) {
            return false;
        }
        String settingsName = getSettingsName(clazz);
        return settingsName.equals(name);
    }

    private static boolean hasSettingsAnnotation(Class<?> clazz) {
        return clazz.isAnnotationPresent(Settings.class);
    }

    private static String getSettingsName(Class<?> clazz) {
        Settings settingsAnnotation = clazz.getAnnotation(Settings.class);
        return settingsAnnotation.name();
    }
}
