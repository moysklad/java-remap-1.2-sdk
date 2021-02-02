package ru.moysklad.remap_1_2.utils.params;

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
    protected String render(String host) {
        return String.valueOf(value);
    }
}
