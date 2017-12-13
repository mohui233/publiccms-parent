package com.publiccms.common.tools;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class DateFormatUtil {
    private static ThreadLocal<Map<String, DateFormat>> threadLocal = new ThreadLocal<>();

    public static DateFormat getDateFormat(String pattern) {
        Map<String, DateFormat> map = threadLocal.get();
        DateFormat format = null;
        if (map == null) {
            map = new HashMap<String, DateFormat>();
            format = new SimpleDateFormat(pattern);
            map.put(pattern, format);
            threadLocal.set(map);
        } else {
            format = map.get(pattern);
            if (format == null) {
                format = new SimpleDateFormat(pattern);
                map.put(pattern, format);
            }
        }
        return format;
    }
}
