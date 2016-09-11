package io.digitalreactor.web.dto;

/**
 * Created by ingvard on 08.09.16.
 */
public class NewAccountUI {
    private String email;
    private Long counterId;
    private String name;
    private String sessionId;

    public NewAccountUI() {
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Long getCounterId() {
        return counterId;
    }

    public String getSessionId() {
        return sessionId;
    }
}
