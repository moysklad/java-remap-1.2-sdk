package com.lognex.api.responses.metadata;

import com.google.gson.annotations.SerializedName;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Список Метаданных Контрагентов
 */

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public final class CounterpartyMetadataResponse extends MetadataAttributeSharedStatesResponse {
    @SerializedName("tags")
    private List<String> groups;
}
