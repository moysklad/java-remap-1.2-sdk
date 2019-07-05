package com.lognex.api.utils.params;

import com.lognex.api.entities.AttributeEntity;
import com.lognex.api.entities.Meta;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.utils.MetaHrefUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.lognex.api.utils.Constants.DATE_FORMAT_PATTERN;

public class AttributeFilterParam extends ApiParam {
    private final AttributeEntity key;
    private final Object value;
    private final FilterType filterType;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT_PATTERN);

    private AttributeFilterParam(AttributeEntity key, FilterType filterType, Object value) {
        super(Type.attributeFilter);
        this.key = key;
        this.value = value;
        this.filterType = filterType;
    }

    public static AttributeFilterParam filterEq(AttributeEntity key, Object value) {
        return filter(key, FilterType.equals, value);
    }

    public static AttributeFilterParam filterNot(AttributeEntity key, Object value) {
        return filter(key, FilterType.not_equals, value);
    }

    public static AttributeFilterParam filter(AttributeEntity key, FilterType filterType, Object value) {
        return new AttributeFilterParam(key, filterType, value);
    }

    @Override
    protected String render(String host) {
        StringBuilder filterString = new StringBuilder();
        if (key == null) {
            throw new IllegalArgumentException("key не может быть null");
        } else if (key.getEntityType() == null) {
            throw new IllegalArgumentException("key.entityType не может быть null");
        } else if (key.getId() == null) {
            throw new IllegalArgumentException("key.id не может быть null");
        }
        filterString.append(host)
                .append("/entity/")
                .append(key.getEntityType().getApiName())
                .append("/metadata/attributes/")
                .append(key.getId())
                .append(filterType.str);

        switch (key.getType()) {
            case stringValue:
            case longValue:
            case doubleValue:
            case fileValue:
            case booleanValue:
            case textValue:
            case linkValue:
                filterString.append(value.toString());
                break;
            case timeValue:
                filterString.append(formatter.format((LocalDateTime) value));
                break;
            default:
                if (value != null) {
                    if (MetaEntity.class.isAssignableFrom(value.getClass())) {
                        filterString.append(MetaHrefUtils.makeHref(Meta.Type.find(((MetaEntity) value).getClass()), value, host));
                    } else {
                        throw new IllegalArgumentException("Неизвестный тип данных дополнительного поля: " + value.getClass().getSimpleName());
                    }
                }
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
