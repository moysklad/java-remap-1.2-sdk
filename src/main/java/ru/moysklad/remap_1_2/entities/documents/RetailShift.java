package ru.moysklad.remap_1_2.entities.documents;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.moysklad.remap_1_2.entities.RetailStore;
import ru.moysklad.remap_1_2.entities.Store;
import ru.moysklad.remap_1_2.entities.agents.Organization;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RetailShift extends DocumentEntity {
    private String externalCode;
    private LocalDateTime created;
    private LocalDateTime closeDate;
    private Double receivedCash;
    private Store store;
    private String syncId;
    private Double proceedsNoCash;
    private Organization organization;
    private RetailStore retailStore;
    private Double proceedsCash;
    private Double receivedNoCash;

    public RetailShift(String id) {
        super(id);
    }
}
