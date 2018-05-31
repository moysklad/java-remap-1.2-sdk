package com.lognex.api;

import com.lognex.api.entities.Meta;
import com.lognex.api.entities.MetaEntity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public interface TestAsserts {
    default void assertListMeta(MetaEntity me, Meta.Type expectedType) {
        assertNotNull(me);
        assertNotNull(me.meta);
        assertListMeta(me.meta, expectedType);
    }

    default void assertListMeta(Meta meta, Meta.Type expectedType) {
        assertNotNull(meta);
        assertNotNull(meta.href);
        assertNotNull(meta.size);
        assertNotNull(meta.limit);
        assertNotNull(meta.offset);
        assertEquals(Meta.MediaType.json, meta.mediaType);
        assertEquals(expectedType, meta.type);
    }

    default void assertEntityMeta(MetaEntity me, Meta.Type expectedType, boolean withUuid) {
        assertNotNull(me);
        assertNotNull(me.meta);
        assertEntityMeta(me.meta, expectedType, withUuid);
    }

    default void assertEntityMeta(Meta meta, Meta.Type expectedType, boolean withUuid) {
        assertNotNull(meta.href);
        assertEquals(expectedType, meta.type);
        assertEquals(Meta.MediaType.json, meta.mediaType);
        if (withUuid) assertNotNull(meta.uuidHref);
    }
}
