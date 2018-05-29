package com.lognex.api.entities;

import com.lognex.api.LognexApi;
import com.lognex.api.utils.HttpRequestBuilder;
import com.lognex.api.utils.LognexApiException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.IOException;

/**
 * Состояние
 */

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class State extends MetaEntity implements Fetchable {
    public String id;
    public String accountId;
    public String name;
    public Integer color;
    public String stateType;    // TODO enum
    public String entityType;   // TODO enum

    @Override
    public void fetch(LognexApi api) throws IOException, LognexApiException {
        this.set(
                HttpRequestBuilder.url(api, meta.href).get(State.class)
        );
    }
}
