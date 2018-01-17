package com.lognex.api.response;

import com.lognex.api.model.base.Entity;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@Getter
@Slf4j
public class ApiResponse {

    private int status;
    private List<? extends Entity> entities;
    private List<Header> headers;
    private Context context;
    private byte[] content;

    public ApiResponse(byte[] content, int status, List<Entity> entities, List<Header> headers, Context context){
        this(status, headers, context);
        this.entities = entities;
        this.content = content;
    }

    public ApiResponse(int status, List<Header> headers, byte[] content){
        this(status, headers, (Context) null);
        this.content = Arrays.copyOf(content, content.length);
    }

    public ApiResponse(int status, List<Header> headers, Context context) {
        this.status = status;
        this.headers = headers;
        this.context = context;
    }

    public void saveContent(File file) throws IOException {
        if (content != null) {
            try (FileOutputStream stream = new FileOutputStream(file)) {
                stream.write(content);
            }
        }
    }
}
