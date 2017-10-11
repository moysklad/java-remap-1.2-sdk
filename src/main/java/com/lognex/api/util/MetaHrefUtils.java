package com.lognex.api.util;

import com.lognex.api.model.base.AbstractEntity;
import com.lognex.api.model.entity.CustomEntity;
import lombok.NoArgsConstructor;

import static com.lognex.api.util.Constants.ENTITY_PATH;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class MetaHrefUtils {

    public static ID getId(String href) {
        String[] split = href.split("/");
        return new ID(split[split.length-1]);
    }

    public static <T> String makeHref(Type type, T value) {
        StringBuilder sb = new StringBuilder(Constants.HOST_URL);
        if (value instanceof AbstractEntity){
            sb.append("/").append(ENTITY_PATH);
        }
        sb.append("/").append(type.name());
        if (value instanceof AbstractEntity){
            if (value instanceof CustomEntity) {
                sb.append("/").append(((CustomEntity)value).getCustomDictionaryId())
                        .append("/").append(((AbstractEntity) value).getId().getValue());
            } else {
                sb.append("/").append(((AbstractEntity) value).getId().getValue());
            }
        }
        return sb.toString();
    }
}
