package com.example.severdemo.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class StringUtils {
    public static boolean isNullOrEmpty(String content) {
        return (content == null || content.equals(""));
    }

    public static LocalDate stringToLocalDate(String date, String pattern) {
        try {
            return LocalDate.parse(date, DateTimeFormatter.ofPattern(pattern));
        } catch (Exception e) {
            return null;
        }
    }

    // chuyen string time vef Instant
    public static Instant stringToInstant(String date, String pattern, boolean isEndOfDay) {
        if (isNullOrEmpty(date)) return null;
        try {
            LocalDate localDate = stringToLocalDate(date, pattern);
            if (isEndOfDay) return localDate.atTime(23, 59, 59, 99).atZone(ZoneId.systemDefault()).toInstant();
            return localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        } catch (Exception e) {
            return null;
        }
    }
}
