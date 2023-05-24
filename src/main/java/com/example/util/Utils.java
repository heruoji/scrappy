package com.example.util;

import com.example.exception.ContainerException;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Utils {

    public static String extractBaseUrl(String originUrl) {
        URL url = null;
        try {
            url = new URL(originUrl);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        return url.getProtocol() + "://" + url.getHost();
    }

    public static List<File> getAllClassFiles() {
        String classRootPath = getClassRootPath();
        return getClassFilesInDir(new File(classRootPath));
    }

    private static String getClassRootPath() {
        return Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("")).getPath();
    }

    private static List<File> getClassFilesInDir(File root) {
        List<File> classFiles = new ArrayList<>();

        File[] files = root.listFiles();
        assert files != null;
        for (File file : files) {
            if (file.isDirectory()) {
                classFiles.addAll(getClassFilesInDir(file));
                continue;
            }
            if (hasClassExtension(file)) {
                classFiles.add(file);
            }
        }
        return classFiles;
    }

    private static boolean hasClassExtension(File file) {
        return file.getName().endsWith(".class");
    }


    public static Class<?> convertFileToClazz(File file){
        String className = extractClassName(file);
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static String extractClassName(File classFile) {
        return classFile.getAbsolutePath()
                .replace(getClassRootPath(), "")
                .replaceAll("/", ".")
                .replace(".class", "");
    }
}
