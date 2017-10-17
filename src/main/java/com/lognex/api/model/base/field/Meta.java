package com.lognex.api.model.base.field;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lognex.api.model.base.AbstractEntity;
import com.lognex.api.util.Type;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import static com.lognex.api.util.Constants.ENTITY_PATH;
import static com.lognex.api.util.Constants.DEFAULT_HOST_URL;
import static com.lognex.api.util.Constants.METADATA_PATH;
import static com.lognex.api.util.MetaHrefUtils.makeHref;

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
        this.type = type.getApiName();
        this.href = makeHref(type, value);
        this.metadataHref = makeMetadataHref(type, value);
    }

    private String makeMetadataHref(Type type, T value) {
        if (value instanceof AbstractEntity) {
            return DEFAULT_HOST_URL + "/" + ENTITY_PATH + "/" + type.getApiName() + "/" + METADATA_PATH;
        }
        return null;
    }


}
