package com.lognex.api.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Тип данных, которые приходят в ответ от сервера, либо отправляются в теле запроса
 */
public enum MediaType {
    @SerializedName("application/json") json,
    @SerializedName("application/octet-stream") octet_stream,
    @SerializedName("image/png") png
}
