package com.lognex.api.response;

import com.lognex.api.model.base.Entity;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;

import java.util.*;

@Getter
@Slf4j
public class ApiResponse {

    private int status;
    private Set<ApiError> errors;
    private List<? extends Entity> entities;
    private List<Header> headers;
    private Context context;

    public ApiResponse(int status, Set<ApiError> errors, List<Entity> entities, List<Header> headers, Context context){
        this.status = status;
        this.errors = errors;
        this.entities = entities;
        this.headers = headers;
        this.context = context;
    }

    public boolean hasErrors(){
        return !errors.isEmpty();
    }
}
