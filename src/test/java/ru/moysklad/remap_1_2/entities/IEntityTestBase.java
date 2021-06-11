package ru.moysklad.remap_1_2.entities;

import ru.moysklad.remap_1_2.clients.EntityClientBase;

public interface IEntityTestBase {
    SimpleEntityManager getSimpleEntityManager();

    default EntityClientBase entityClient() {
        return null;
    }

    default Class<? extends MetaEntity> entityClass() {
        return null;
    }
}
