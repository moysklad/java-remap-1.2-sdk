package com.lognex.api.util;

public class MetaHrefParser {

    /*TODO точно ли брать последний элемент будет работать везде? Пока этого достаточно*/
    public static ID getId(String href) {
        String[] split = href.split("/");
        return new ID(split[split.length-1]);
    }
}
