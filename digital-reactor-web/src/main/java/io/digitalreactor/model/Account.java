package io.digitalreactor.model;

import jdk.nashorn.internal.ir.annotations.Immutable;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

/**
 * Created by MStepachev on 07.09.2016.
 */
@Document(collection = "accounts")
public class Account {
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
}
