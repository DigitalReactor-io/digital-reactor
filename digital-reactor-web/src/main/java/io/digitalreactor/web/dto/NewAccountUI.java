package io.digitalreactor.web.dto;

/**
 * Created by ingvard on 08.09.16.
 */
public class NewAccountUI {
    private String email;
    private Long counterId;
    private String sessionToken;

    public NewAccountUI() {
    }

    public String getEmail() {
        return email;
    }

    public Long getCounterId() {
        return counterId;
    }

    public String getSessionToken() {
        return sessionToken;
    }
}
