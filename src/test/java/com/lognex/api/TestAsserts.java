package com.lognex.api;

import com.lognex.api.entities.Meta;
import com.lognex.api.entities.MetaEntity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public interface TestAsserts {
    default void assertListMeta(MetaEntity me, Meta.Type expectedType) {
        assertNotNull(me);
        assertNotNull(me.getMeta());
        assertListMeta(me.getMeta(), expectedType);
    }

    default void assertListMeta(Meta meta, Meta.Type expectedType) {
        assertNotNull(meta);
        assertNotNull(meta.getHref());
        assertNotNull(meta.getSize());
        assertNotNull(meta.getLimit());
        assertNotNull(meta.getOffset());
        assertEquals(Meta.MediaType.json, meta.getMediaType());
        assertEquals(expectedType, meta.getType());
    }

    default void assertEntityMeta(MetaEntity me, Meta.Type expectedType, boolean withUuid) {
        assertNotNull(me);
        assertNotNull(me.getMeta());
        assertEntityMeta(me.getMeta(), expectedType, withUuid);
    }

    default void assertEntityMeta(Meta meta, Meta.Type expectedType, boolean withUuid) {
        assertNotNull(meta.getHref());
        assertEquals(expectedType, meta.getType());
        assertEquals(Meta.MediaType.json, meta.getMediaType());
        if (withUuid) assertNotNull(meta.getUuidHref());
    }
}
