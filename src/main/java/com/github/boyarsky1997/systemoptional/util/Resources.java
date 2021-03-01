package com.github.boyarsky1997.systemoptional.util;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class Resources {
    private static final Logger logger = Logger.getLogger(Resources.class);

    public static String asString(String path) {
        logger.info("Вичитую файл... ");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Resources.class.getResourceAsStream(path)));
        return bufferedReader.lines()
                .collect(Collectors.joining(System.lineSeparator()));
    }

    public static String load(String path) {
        logger.info("Вичитую файл... ");
        return new BufferedReader(new InputStreamReader(Resources.class.getResourceAsStream(path)))
                .lines()
                .collect(Collectors.joining(System.lineSeparator()));
    }
}