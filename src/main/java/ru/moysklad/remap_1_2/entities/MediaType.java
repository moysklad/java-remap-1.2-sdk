package ru.moysklad.remap_1_2.entities;


import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Тип данных, которые приходят в ответ от сервера, либо отправляются в теле запроса
 */
public enum MediaType {
    @JsonProperty("application/json") json,
    @JsonProperty("application/octet-stream") octet_stream,
    @JsonProperty("image/png") png
}
