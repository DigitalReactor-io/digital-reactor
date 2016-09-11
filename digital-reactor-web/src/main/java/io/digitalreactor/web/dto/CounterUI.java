package io.digitalreactor.web.dto;

import io.digitalreactor.vendor.yandex.model.Counter;

/**
 * Created by ingvard on 10.09.16.
 */
public class CounterUI {
    private String name;
    private String counterId;

    public CounterUI(String name, String counterId) {
        this.name = name;
        this.counterId = counterId;
    }

    public CounterUI(Counter counter) {
        this.name = counter.getName();
        this.counterId = String.valueOf(counter.getId());
    }

    public String getName() {
        return name;
    }

    public String getCounterId() {
        return counterId;
    }
}
