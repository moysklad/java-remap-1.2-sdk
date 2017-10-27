package com.lognex.api.util;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class Constants {
    public static final String DEFAULT_HOST_URL = "https://online.moysklad.ru";

    public static final String APPLICATION_JSON_UTF8 = "application/json;charset=utf-8";
    public static final String APPLICATION_OCTET_STREAM = "application/octet-stream";

    public static final String ENTITY_PATH = "entity";
    public static final String METADATA_PATH = "metadata";
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
}
