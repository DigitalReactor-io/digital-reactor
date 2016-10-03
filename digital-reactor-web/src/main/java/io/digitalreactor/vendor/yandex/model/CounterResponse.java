package io.digitalreactor.vendor.yandex.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by ingvard on 11.09.16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CounterResponse {
    private List<Counter> counters;

    public CounterResponse() {
    }

    public List<Counter> getCounters() {
        return counters;
    }
}
