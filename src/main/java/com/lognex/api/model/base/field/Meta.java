package com.lognex.api.model.base.field;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lognex.api.model.base.AbstractEntity;
import com.lognex.api.util.Constants;
import com.lognex.api.util.Type;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import static com.lognex.api.util.Constants.ENTITY_PATH;
import static com.lognex.api.util.Constants.DEFAULT_HOST_URL;
import static com.lognex.api.util.Constants.METADATA_PATH;
import static com.lognex.api.util.MetaHrefUtils.makeHref;
import static org.apache.http.util.TextUtils.isEmpty;

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
        this(type, value, DEFAULT_HOST_URL);
    }

    public Meta(Type type, T value, String host) {
        if (isEmpty(host)) {
            host = Constants.DEFAULT_HOST_URL;
        }
        this.type = type.getApiName();
        this.href = makeHref(type, value, host);
        this.metadataHref = makeMetadataHref(type, value, host);
    }

    private String makeMetadataHref(Type type, T value, String host) {
        if (value instanceof AbstractEntity) {
            return host + "/" + ENTITY_PATH + "/" + type.getApiName() + "/" + METADATA_PATH;
        }
        return null;
    }


}
