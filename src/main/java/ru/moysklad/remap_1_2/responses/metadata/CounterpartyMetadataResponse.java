package ru.moysklad.remap_1_2.responses.metadata;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.moysklad.remap_1_2.entities.Attribute;

import java.util.List;

/**
 * Список Метаданных Контрагентов
 */

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public final class CounterpartyMetadataResponse extends MetadataAttributeSharedStatesResponse<Attribute> {
    @JsonProperty("tags")
    private List<String> groups;
}
