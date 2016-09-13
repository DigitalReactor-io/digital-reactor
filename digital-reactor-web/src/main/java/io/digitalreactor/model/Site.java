package io.digitalreactor.model;

import java.util.UUID;

/**
 * Created by MStepachev on 07.09.2016.
 */
public class Site {
    private String id;
    private String name;
    private YandexCounterAccess yandexAccess;

    public Site(String name, YandexCounterAccess yandexAccess) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.yandexAccess = yandexAccess;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public YandexCounterAccess getYandexAccess() {
        return yandexAccess;
    }
}
