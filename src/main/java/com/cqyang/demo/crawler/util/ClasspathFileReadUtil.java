package com.cqyang.demo.crawler.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class ClasspathFileReadUtil {
    private static final ClassLoader classLoader = ClasspathFileReadUtil.class.getClassLoader();

    public static InputStream getFileAsStream(String path) {
        return classLoader.getResourceAsStream(path);
    }

    public static String getStringByInputStream(InputStream inputStream) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.joining("\n"));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
