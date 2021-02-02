package ru.moysklad.remap_1_2.utils.params;

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
    protected String render(String host) {
        return String.valueOf(value);
    }
}
