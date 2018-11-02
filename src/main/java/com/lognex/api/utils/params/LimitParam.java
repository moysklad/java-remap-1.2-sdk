package com.lognex.api.utils.params;

public class LimitParam extends ApiParam {
    private final int value;

    private LimitParam(int value) {
        super(Type.limit);
        this.value = value;
    }

    public static LimitParam limit(int value) {
        return new LimitParam(value);
    }

    @Override
    protected String render() {
        return String.valueOf(value);
    }
}
