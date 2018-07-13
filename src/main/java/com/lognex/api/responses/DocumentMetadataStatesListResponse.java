package com.lognex.api.responses;

import com.lognex.api.entities.StateEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Список Метаданных Документов, у которые возвращаются только статусы
 */

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public final class DocumentMetadataStatesListResponse extends MetadataListResponse {
    private List<StateEntity> states;
}
