package com.lognex.api.request.filter;

import com.lognex.api.model.base.AbstractEntity;
import com.lognex.api.util.Constants;
import com.lognex.api.util.MetaHrefUtils;
import com.lognex.api.util.Type;
import static com.google.common.base.Preconditions.checkNotNull;

public class EntityRefFilter extends Filter<AbstractEntity> {
    public EntityRefFilter(String fieldName, FilterOperator operator, AbstractEntity value) {
        super(fieldName, operator, value);
    }

    @Override
    public String toFilterString() {
        return toFilterString(Constants.DEFAULT_HOST_URL);
    }

    @Override
    public String toFilterString(String host) {
        checkNotNull(value.getId());
        Type type = Type.find(value.getClass());
        String href = MetaHrefUtils.makeHref(type, value, host);
        return fieldName + operator.getSign() + href;
    }
}
