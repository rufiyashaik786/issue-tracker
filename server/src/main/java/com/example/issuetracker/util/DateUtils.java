package com.example.issuetracker.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class DateUtils {

    private static final DateTimeFormatter DEFAULT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private DateUtils() {}

    public static String format(LocalDateTime dt) {
        return dt == null ? null : dt.format(DEFAULT);
    }
}
