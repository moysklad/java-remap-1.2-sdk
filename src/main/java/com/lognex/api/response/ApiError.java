package com.lognex.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiError {

    @JsonProperty
    protected String error;

    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    protected int code;

    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_NULL)
    protected String parameter;

    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String moreInfo;

    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private int line;

    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private int column;

}
