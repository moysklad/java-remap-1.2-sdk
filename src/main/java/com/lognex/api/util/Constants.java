package com.lognex.api.util;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class Constants {
    public static final String HOST_URL = "https://online.moysklad.ru/api/remap/1.1/entity";
    public static final String HOST_URL2 = "https://online.moysklad.ru/api/remap/1.1";

    public static final String APPLICATION_JSON = "application/json;charset=utf-8";


    public static final String ENTITY_PATH = "entity";
    public static final String METADATA_PATH = "metadata";
}
