package com.lognex.api.responses.metadata;

import com.lognex.api.entities.CustomEntity;
import com.lognex.api.entities.GlobalMetadataEntity.CreateSharedOption;
import com.lognex.api.entities.MetaEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CompanySettingsMetadataResponse extends MetaEntity {
    /**
     * Пользовательские справочники
     */
    private List<CustomEntityMetadata> customEntities;

    @Getter
    @Setter
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    public static class CustomEntityMetadata extends CreateSharedOption {
        private CustomEntity entityMeta;
    }
}
