package ru.moysklad.remap_1_2.entities.agents;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.moysklad.remap_1_2.entities.Group;

import java.time.LocalDateTime;

/**
 * Подразделение юридического лица
 */
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public final class OrganizationBranch extends Agent {
    private String syncId;
    private Employee owner;
    private Boolean shared;
    private Group group;
    private LocalDateTime updated;
    private String description;
    private String code;
    private String externalCode;
    private Boolean archived;
    private LocalDateTime created;

    /**
     * Юридическое лицо, к которому относится подразделение
     */
    private Organization organization;

    /**
     * КПП
     */
    private String kpp;

    /**
     * Фактический адрес
     */
    private String actualAddress;

    public OrganizationBranch(String id) {
        super(id);
    }
}
