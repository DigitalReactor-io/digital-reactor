package io.digitalreactor.web.contract.dto;

/**
 * Created by MStepachev on 08.09.2016.
 */
public class EmailCheckUI {
    private String email;

    public EmailCheckUI() {
    }

    public EmailCheckUI(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
