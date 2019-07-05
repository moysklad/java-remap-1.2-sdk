package com.lognex.api.entities.builders;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.ApiClient;
import com.lognex.api.entities.MediaType;
import com.lognex.api.entities.Meta;
import lombok.NonNull;

public class MetaBuilder {
    private MetaBuilder(LognexApi api, String id, ApiClient client) {
        this.api = api;
        this.id = id;
        this.client = client;
        meta = new Meta();
    }

    static public MetaBuilder getBuilder(@NonNull LognexApi api, @NonNull String id, @NonNull ApiClient client) {
        return new MetaBuilder(api, id, client);
    }

    public MetaBuilder setMetadataHref() {
        meta.setMetadataHref(api.getHost() + client.path() + "metadata");
        return this;
    }

    public MetaBuilder setType(ApiClient client) {
        meta.setType(Meta.Type.valueOf(client
                .entityClass()
                .toString()
                .replace("Entity", "")
                .replace("Document", "")
                .toLowerCase()
        ));
        return this;
    }

    public Meta build() {
        meta.setHref(api.getHost() + client.path() + id);
        meta.setType(Meta.Type.valueOf(client
                .entityClass()
                .toString()
                .replace("Entity", "")
                .replace("Document", "")
                .toLowerCase()
        ));
        meta.setMediaType(MediaType.json);
        return meta;
    }

    private Meta meta;
    private LognexApi api;
    private String id;
    private ApiClient client;
}
