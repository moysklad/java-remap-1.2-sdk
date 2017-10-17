package com.lognex.api.model.base.field;

import lombok.Getter;

@Getter
public class FileRef {
    public FileRef(String href, String mediaType){
        this.href = href;
        this.mediaType = mediaType;
    }

    private final String href;
    private final String mediaType;
}
