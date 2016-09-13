package io.digitalreactor.web.contract.dto;

/**
 * Created by MStepachev on 12.09.2016.
 */
public class SiteUI {
    private String name;
    private String id;

    public SiteUI(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
