package com.lognex.api.entities;

import com.lognex.api.LognexApi;
import com.lognex.api.utils.HttpRequestExecutor;
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
    private String id;
    private String accountId;
    private String name;
    private Integer color;
    private String stateType;    // TODO enum
    private String entityType;   // TODO enum

    @Override
    public void fetch(LognexApi api) throws IOException, LognexApiException {
        this.set(
                HttpRequestExecutor.url(api, meta.getHref()).get(State.class)
        );
    }
}
