package com.lognex.api.converter.field;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lognex.api.model.base.AbstractEntity;
import com.lognex.api.util.Constants;
import com.lognex.api.util.Type;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import static com.lognex.api.util.Constants.ENTITY_PATH;
import static com.lognex.api.util.Constants.HOST_URL2;
import static com.lognex.api.util.Constants.METADATA_PATH;

@Getter
@Setter
public class Meta<T> {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String href;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String metadataHref;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String type;
    @Setter(value = AccessLevel.PRIVATE)
    private String mediaType = "application/json";

    public Meta(Type type, T value){
        this.type = type.name();
        this.href = makeHref(type, value);
        this.metadataHref = makeMetadataHref(type, value);
    }

    private String makeMetadataHref(Type type, T value) {
        if (value instanceof AbstractEntity) {
            return new StringBuilder(HOST_URL2).append("/")
                    .append(ENTITY_PATH).append("/").append(type.name()).append("/").append(METADATA_PATH).toString();
        }
        return null;
    }

    private String makeHref(Type type, T value) {
        StringBuilder sb = new StringBuilder(Constants.HOST_URL2);
        if (value instanceof AbstractEntity){
            sb.append("/").append(ENTITY_PATH);
        }
        sb.append("/").append(type.name());
        if (value instanceof AbstractEntity){
            sb.append("/").append(((AbstractEntity)value).getId().getValue());
        }
        return sb.toString();
    }

}
