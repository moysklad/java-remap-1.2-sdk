package ru.moysklad.remap_1_2.utils.params;

public class OrderParam extends ApiParam {
    private final String fieldName;
    private final Direction direction;

    private OrderParam(String fieldName, Direction direction) {
        super(Type.order);
        this.fieldName = fieldName;
        this.direction = direction;
    }

    public static OrderParam order(String fieldName) {
        return new OrderParam(fieldName, null);
    }

    public static OrderParam order(String fieldName, Direction direction) {
        return new OrderParam(fieldName, direction);
    }

    @Override
    protected String render(String host) {
        return fieldName + (direction == null ? "" : "," + direction.name());
    }

    public enum Direction {
        /**
         * По возрастанию
         */
        asc,

        /**
         * По убыванию
         */
        desc
    }
}
