package io.digitalreactor.model;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

/**
 * Created by MStepachev on 07.09.2016.
 */
@Document(collection = "accounts")
public class Account {
    @Field
    private String id;
    @Indexed(unique = true)
    private String email;
    private String password;
    private LocalDate registrationDate;
    private List<Site> sites;

    public Account() {
    }

    public Account(String email, String password, LocalDate registrationDate, Site site) {
        this.email = email;
        this.password = password;
        this.registrationDate = registrationDate;
        this.sites = Arrays.asList(site);
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public List<Site> getSites() {
        return sites;
    }
}
