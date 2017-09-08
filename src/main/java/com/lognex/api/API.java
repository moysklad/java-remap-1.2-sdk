package com.lognex.api;

import com.lognex.api.endpoint.DocumentEndpoint;
import com.lognex.api.model.base.AbstractEntity;
import com.lognex.api.util.Constants;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

public class API {
    public RequestBuilder initRequest(String login, String password) {
        return new RequestBuilder(login, password);
    }

    public static class RequestBuilder {
        private String login;
        private String password;
        /*TODO
        * 1) либо продумать механизм валидации переданной строки
        * 2) либо передавать сюда объекты ( как тогда быть с вложенными сущностями, пример demand.agent ? */
        private Set<String> expand = new HashSet<>();
        private Optional<Integer> limit = Optional.empty();
        private Optional<Integer> offset = Optional.empty();
        private String type;

        RequestBuilder(String login, String password) {
            this.login = login;
            this.password = password;
        }

        public RequestBuilder expand(String expandParam) {
            this.expand.add(expandParam);
            return this;
        }

        public RequestBuilder limit(int limit) {
            this.limit = Optional.of(limit);
            return this;
        }

        public RequestBuilder offset(int offset) {
            this.offset = Optional.of(offset);
            return this;
        }

        public RequestBuilder type(String type) {
            this.type = type;
            return this;
        }

        public AbstractEntity read(DocumentEndpoint documentEndpoint) {
            validate();
            return documentEndpoint.readDocument(this.build());
        }

        private void validate() {
            checkNotNull(login, "login is missing");
            checkNotNull(password, "password is missing");
            checkNotNull(type, "type is missing");

            checkState(!expand.stream()
                    .anyMatch(s -> s.split("\\.").length > 3),
                    "max depth of expand equals 3");
            limit.ifPresent(integer -> checkState(integer >= 0, "limit should be greater than or equal to zero"));
            offset.ifPresent(integer -> checkState(integer >= 0, "offset should be greater than or equal to zero"));
        }

        private String build() {
            StringBuilder sb = new StringBuilder(Constants.HOST_URL);
            sb.append('/');
            sb.append(type);
            if ( limit != null || offset != null | expand != null) {
                sb.append('?');
            }
            limit.ifPresent(integer -> appendParam(sb, "limit", integer));
            offset.ifPresent(integer -> appendParam(sb, "offset", integer));
            if (!expand.isEmpty()) {
                appendParam(sb, "expand", buildSetParam(expand));
            }
            return sb.toString();
        }

        private String buildSetParam(Set<String> params) {
            return params.stream()
                    .collect(Collectors.joining(","));
        }

        private StringBuilder appendParam(final StringBuilder sb, String paramName, Object param) {
            return sb.charAt(sb.length()-1) != '?'
                    ? sb.append('&' + paramName + '=' + param)
                    : sb.append(paramName + '=' + param);
        }
    }
}
