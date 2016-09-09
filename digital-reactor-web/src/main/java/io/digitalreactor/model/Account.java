package io.digitalreactor.model;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by MStepachev on 07.09.2016.
 */
public class Account {
    private String email;
    private String password;
    private LocalDate registrationDate;
    private List<Site> sites;

}
