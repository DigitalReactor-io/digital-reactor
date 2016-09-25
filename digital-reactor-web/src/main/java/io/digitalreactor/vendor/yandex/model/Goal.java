package io.digitalreactor.vendor.yandex.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by ingvard on 24.09.16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Goal {
    private int id;
    private String name;
    private String type;
    @JsonProperty("is_retargeting")
    private boolean isRetargeting;

    public Goal() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public boolean isRetargeting() {
        return isRetargeting;
    }
}
