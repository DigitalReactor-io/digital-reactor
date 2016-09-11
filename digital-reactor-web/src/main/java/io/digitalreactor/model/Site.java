package io.digitalreactor.model;

/**
 * Created by MStepachev on 07.09.2016.
 */
public class Site {
    private String name;
    private YandexCounterAccess yandexAccess;

    public Site(String name, YandexCounterAccess yandexAccess) {
        this.name = name;
        this.yandexAccess = yandexAccess;
    }
}
