package com.lognex.api.util;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class MetaHrefParser {

    /*TODO точно ли брать последний элемент будет работать везде? Пока этого достаточно*/
    public static ID getId(String href) {
        String[] split = href.split("/");
        return new ID(split[split.length-1]);
    }
}
