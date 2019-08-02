package com.lognex.api.utils.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lognex.api.entities.Meta;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class JsonUtils {
    public static Gson createGsonWithMetaAdapter() {
        return new GsonBuilder().registerTypeAdapter(Meta.Type.class, new Meta.Type.Serializer()).create();
    }
}
