package io.digitalreactor.web.contract.dto;

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

    public String getName() {
        return name;
    }

    public String getCounterId() {
        return counterId;
    }
}
