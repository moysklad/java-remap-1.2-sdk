package com.lognex.api.utils.params;

public class OffsetParam extends ApiParam {
    private final int value;

    private OffsetParam(int value) {
        super(Type.offset);
        this.value = value;
    }

    public static OffsetParam offset(int value) {
        return new OffsetParam(value);
    }

    @Override
    protected String render() {
        return String.valueOf(value);
    }
}
