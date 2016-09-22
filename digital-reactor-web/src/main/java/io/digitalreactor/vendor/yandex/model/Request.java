package io.digitalreactor.vendor.yandex.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Arrays;
import java.util.List;

/**
 * Created by flaidzeres on 12.06.2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Request {
    private List<String> directClientLogins;
    private List<String> ids;
    private String date1;
    private String date2;
    private List<String> dimensions;
    private List<String> metrics;
    private String group;
    private String attribution;
    private String preset;
    private String lang;
    private Integer limit;
    private Integer offset;
    private Boolean pretty;
    private String token;
    private String prefix;


    private Request() {
    }

    public static Builder of() {
        return new Builder();
    }

    public static class Builder {
        private Request request = new Request();

        public Builder preset(String preset) {
            request.preset = preset;
            return this;
        }

        public Builder prefix(String prefix) {
            request.prefix = prefix;
            return this;
        }

        public Builder directClientLogins(String... directClientLogins) {
            request.directClientLogins = Arrays.asList(directClientLogins);
            return this;
        }

        public Builder ids(String... ids) {
            request.ids = Arrays.asList(ids);
            return this;
        }

        public Builder date1(String date1) {
            request.date1 = date1;
            return this;
        }

        public Builder date2(String date2) {
            request.date2 = date2;
            return this;
        }

        public Builder dimensions(String... dimensions) {
            request.dimensions = Arrays.asList(dimensions);
            return this;
        }

        public Builder metrics(String... metrics) {
            request.metrics = Arrays.asList(metrics);
            return this;
        }

        public Builder group(String group) {
            request.group = group;
            return this;
        }

        public Builder attribution(String attribution) {
            request.attribution = attribution;
            return this;
        }

        public Builder lang(String lang) {
            request.lang = lang;
            return this;
        }

        public Builder limit(Integer limit) {
            request.limit = limit;
            return this;
        }

        public Builder offset(Integer offset) {
            request.offset = offset;
            return this;
        }

        public Builder pretty(Boolean pretty) {
            request.pretty = pretty;
            return this;
        }

        public Builder token(String token) {
            request.token = token;
            return this;
        }

        public Request build() {
            return request;
        }
    }
}
