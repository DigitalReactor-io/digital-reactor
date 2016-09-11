package io.digitalreactor.model;

/**
 * Created by MStepachev on 07.09.2016.
 */
public class YandexCounterAccess {
    private String token;
    private String counterId;

    public YandexCounterAccess(String token, String counterId) {
        this.token = token;
        this.counterId = counterId;
    }
}
