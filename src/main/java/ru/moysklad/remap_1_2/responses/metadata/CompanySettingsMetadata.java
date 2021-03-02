package ru.moysklad.remap_1_2.responses.metadata;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.moysklad.remap_1_2.entities.CustomEntity;
import ru.moysklad.remap_1_2.entities.GlobalMetadata.CreateSharedOption;
import ru.moysklad.remap_1_2.entities.MetaEntity;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CompanySettingsMetadata extends MetaEntity {
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
