package com.lognex.api.utils.params;

import com.lognex.api.entities.Meta;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.utils.MetaHrefUtils;


public class HrefFilterParam extends ApiParam {
    private final String key;
    private final MetaEntity value;
    private final FilterType filterType;

    private HrefFilterParam(String key, FilterType filterType, MetaEntity value) {
        super(Type.hrefFilter);
        this.key = key;
        this.value = value;
        this.filterType = filterType;
    }

    public static HrefFilterParam filterEq(String key, MetaEntity value) {
        return filter(key, FilterType.equals, value);
    }

    public static HrefFilterParam filterNot(String key, MetaEntity value) {
        return filter(key, FilterType.not_equals, value);
    }

    public static HrefFilterParam filter(String key, FilterType filterType, MetaEntity value) {
        return new HrefFilterParam(key, filterType, value);
    }

    @Override
    protected String render(String host) {
        StringBuilder filterString = new StringBuilder();
        filterString.append(key)
                .append(filterType.str);
        if (value != null) {
            filterString.append(MetaHrefUtils.makeHref(Meta.Type.find(value.getClass()), value, host));
        }

        return filterString.toString();
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
