package com.lognex.api.request;

import com.google.common.net.UrlEscapers;
import com.lognex.api.ApiClient;
import com.lognex.api.model.base.Entity;
import com.lognex.api.model.entity.attribute.Attribute;
import com.lognex.api.model.entity.attribute.AttributeValue;
import com.lognex.api.request.filter.*;
import com.lognex.api.request.sort.Sort;
import com.lognex.api.util.Type;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MSReadListRequest extends MSRequest {

    private Type type;
    private Optional<Integer> limit = Optional.empty();
    private Optional<Integer> offset = Optional.empty();
    private Set<Filter> filters = new HashSet<>();
    private List<Sort> sorts = Collections.emptyList();

    public MSReadListRequest(String url, ApiClient client) {
        super(url, client);
    }

    public MSReadListRequest(String url, ApiClient client, Type type) {
        super(url, client);
        this.type = type;
    }

    public MSReadListRequest limit(int limit){
        this.limit = Optional.of(limit);
        return this;
    }

    public MSReadListRequest offset(int offset){
        this.offset = Optional.of(offset);
        return this;
    }

    public MSReadListRequest filters(Set<Filter> filters){
        this.filters = filters;
        return this;
    }

    public MSReadListRequest sorts(Sort... sorts) {
        this.sorts = Stream.of(sorts).collect(Collectors.toList());
        return this;
    }

    public FiltersBuilder buildFilter(){
        return new FiltersBuilder(type);
    }

    @Override
    protected HttpUriRequest buildRequest() {
        StringBuilder urldBuilder = new StringBuilder(getUrl());
        limit.ifPresent(integer -> appendParam(urldBuilder, "limit", integer));
        offset.ifPresent(integer -> appendParam(urldBuilder, "offset", integer));
        if (filters.size() > 0){
            appendParam(urldBuilder, "filter", filters.stream().map(filter -> filter.toFilterString(getClient().getHost())).collect(Collectors.joining(";")));
        }
        if (!sorts.isEmpty()) {
            appendParam(urldBuilder, "order", sorts.stream().map(Sort::toSortString).collect(Collectors.joining(";")));
        }
        addExpandParameter(urldBuilder);
        String uri = urldBuilder.toString();
        String escapedUri = UrlEscapers.urlFragmentEscaper().escape(uri);
        return new HttpGet(escapedUri);
    }

    @Override
    protected boolean hasParameters() {
        return super.hasParameters() || limit.isPresent() || offset.isPresent();
    }


    public class FiltersBuilder {

        private Set<Filter> filters = new HashSet<>();
        private final Type type;

        private FiltersBuilder(Type entityType){
            this.type = entityType;
        }

        public FiltersBuilder filter(String fieldName, FilterOperator operator, Object value){
            filters.add(buildFilter(fieldName, operator, value));
            return this;
        }

        public FiltersBuilder filter(Attribute attribute, FilterOperator operator, AttributeValue value){
            filters.add(buildAttributeFilter(attribute, operator, value));
            return this;
        }

        private Filter buildFilter(String fieldName, FilterOperator operator, Object value){
            if (value instanceof String){
                return new StringFilter(fieldName, operator, (String) value);
            } else if (value instanceof Number){
                return new NumberFilter(fieldName, operator, (Number) value);
            } else if (value instanceof Date){
                return new DateFilter(fieldName, operator, (Date) value);
            } else if (value instanceof Boolean){
                return new BooleanFilter(fieldName, operator, (Boolean) value);
            } else if (value instanceof Entity){
                return new EntityRefFilter(fieldName, operator, (Entity) value);
            } else if (value instanceof Attribute){
                return new AttributeFilter(type, (Attribute)value, operator, ((Attribute) value).getValue());
            } else {
                throw new IllegalStateException(String.format("Filters for type=%s not implemented", value.getClass().getSimpleName()));
            }
        }

        private Filter buildAttributeFilter(Attribute attribute, FilterOperator operator, AttributeValue value){
            return new AttributeFilter(type, attribute, operator, value);
        }

        public MSReadListRequest build(){
            MSReadListRequest.this.filters = filters;
            return MSReadListRequest.this;
        }
    }
}
