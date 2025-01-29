package ru.moysklad.remap_1_2.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.moysklad.remap_1_2.ApiClient;
import ru.moysklad.remap_1_2.utils.ApiClientException;
import ru.moysklad.remap_1_2.utils.HttpRequestExecutor;

import java.io.IOException;

/**
 * Состояние
 */

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class State extends MetaEntity implements Fetchable {
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

    public State(String id) {
        super(id);
    }

    @Override
    public void fetch(ApiClient api) throws IOException, ApiClientException {
        this.set(
                HttpRequestExecutor.url(api, meta.getHref()).get(State.class)
        );
    }

    public enum StateType {
        @JsonProperty("Regular") regular,
        @JsonProperty("Successful") successful,
        @JsonProperty("Unsuccessful") unsuccessful
    }
}
