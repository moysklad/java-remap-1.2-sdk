package com.lognex.api.utils.params;

public class FilterParam extends ApiParam {
    private final String key;
    private final String value;
    private final FilterType filterType;

    private FilterParam(String key, FilterType filterType, String value) {
        super(Type.filter);
        this.key = key;
        this.value = value;
        this.filterType = filterType;
    }

    public static FilterParam filterEq(String key, String value) {
        return filter(key, FilterType.equals, value);
    }

    public static FilterParam filterNot(String key, String value) {
        return filter(key, FilterType.not_equals, value);
    }

    public static FilterParam filter(String key, FilterType filterType, String value) {
        return new FilterParam(key, filterType, value);
    }

    @Override
    protected String render() {
        return key + filterType.str + value;
    }

    public enum FilterType {
        equals("="),
        greater("~"),
        lesser("~"),
        greater_or_equals("~"),
        lesser_or_equals("~"),
        not_equals("!="),
        equivalence("~"),
        equivalence_left("~="),
        equivalence_right("=~");

        private final String str;

        FilterType(String str) {
            this.str = str;
        }

        public String getStr() {
            return str;
        }
    }
}
