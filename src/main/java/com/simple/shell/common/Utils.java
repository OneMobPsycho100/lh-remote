package com.simple.shell.common;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

/**
 * Utils
 *
 * @author wuji
 * @date 2020-12-02 22:20
 */
public class Utils {


    private static final String ZONE = "+8";

    /**
     * 毫秒数转 LocalDateTime
     *
     * @param milliseconds 毫秒数
     * @return LocalDateTime
     */
    public static LocalDateTime ofMilliseconds(Long milliseconds) {
        return new Date(milliseconds).toInstant().atOffset(ZoneOffset.of(ZONE)).toLocalDateTime();
    }
}
