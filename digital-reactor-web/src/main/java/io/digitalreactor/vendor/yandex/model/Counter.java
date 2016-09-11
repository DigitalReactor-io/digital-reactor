package io.digitalreactor.vendor.yandex.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by MStepachev on 09.09.2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Counter {
    private Long id;
    private String name;

    public Counter() {
    }

    public Counter(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
