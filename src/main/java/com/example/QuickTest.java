//package com.example;
//
//import com.example.spider.Spider;
//import com.example.util.Utils;
//import com.microsoft.playwright.*;
//import com.microsoft.playwright.options.AriaRole;
//import com.microsoft.playwright.options.LoadState;
//import com.microsoft.playwright.options.WaitUntilState;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//
//import java.io.File;
//import java.lang.reflect.Field;
//import java.lang.reflect.InvocationTargetException;
//import java.net.MalformedURLException;
//import java.util.ArrayList;
//import java.util.List;
//
//import static com.codeborne.selenide.Selenide.$;
//import static com.codeborne.selenide.Selenide.open;
//
//public class QuickTest {
//    public static void main(String[] args) throws MalformedURLException, NoSuchFieldException, ClassNotFoundException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {
////
//        Spider spider = buildSpider("quote");
//        System.out.println(spider);
//    }
//
//    private static Spider buildSpider(String nameVal) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {
//        String classPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
//        File root = new File(classPath);
//        List<File> list = getClassList(root);
//        for (File file : list) {
//            String className = file.getAbsolutePath()
//                    .replace(classPath, "")
//                    .replaceAll("/", ".")
//                    .replace(".class", "");
//            Class clazz = Class.forName(className);
//            if (clazz.isInterface()) {
//                continue;
//            }
//            if (Spider.class.isAssignableFrom(clazz)) {
//                Field field = clazz.getDeclaredField("name");
//                field.setAccessible(true);
//                String name = (String) field.get(null);
//                if (name.equals(nameVal)) {
//                    return (Spider) clazz.getConstructor().newInstance();
//                }
//            }
//        }
//        return null;
//    }
//
//    private static List<File> getClassList(File target) {
//        List<File> list = new ArrayList<>();
//        File[] files = target.listFiles();
//        for (File file : files) {
//            if (file.isDirectory()) {
//                list.addAll(getClassList(file));
//                continue;
//            }
//            if (file.getName().endsWith(".class")) {
//                list.add(file);
//            }
//        }
//        return list;
//    }
//}
