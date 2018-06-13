package com.lognex.api.entities;

import com.google.gson.annotations.SerializedName;
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
public class StateEntity extends MetaEntity implements Fetchable {
    /**
     * ID в формате UUID
     */
    private String id;

    /**
     * ID учетной записи
     */
    private String accountId;

    /**
     * Наименование Статуса
     */
    private String name;

    /**
     * Цвет Статуса
     */
    private Integer color;

    /**
     * Тип Статуса
     */
    private StateType stateType;

    /**
     * Тип сущности, к которой относится Статус
     */
    private Meta.Type entityType;

    @Override
    public void fetch(LognexApi api) throws IOException, LognexApiException {
        this.set(
                HttpRequestExecutor.url(api, meta.getHref()).get(StateEntity.class)
        );
    }

    public enum StateType {
        @SerializedName("Regular") regular,
        @SerializedName("Successful") successful,
        @SerializedName("Unsuccessful") unsuccessful
    }
}
