package ru.moysklad.remap_1_2.utils.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.NoArgsConstructor;
import ru.moysklad.remap_1_2.entities.Meta;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class JsonUtils {
    public static Gson createGsonWithMetaAdapter() {
        return new GsonBuilder().registerTypeAdapter(Meta.Type.class, new Meta.Type.Serializer()).create();
    }
}
