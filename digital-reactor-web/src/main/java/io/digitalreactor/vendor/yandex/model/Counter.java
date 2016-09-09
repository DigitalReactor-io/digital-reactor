package io.digitalreactor.vendor.yandex.model;

/**
 * Created by MStepachev on 09.09.2016.
 */
public class Counter {
    private long id;
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
