package io.digitalreactor.vendor.yandex.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ingvard on 24.09.16.
 */
public class GoalResponse {
    private List<Goal> goals;

    public GoalResponse() {
        goals = new ArrayList<>();
    }

    public List<Goal> getGoals() {
        return goals;
    }

    public List<String> getGoalsIds() {
        return goals.stream().map(goal -> String.valueOf(goal.getId())).collect(Collectors.toList());
    }
}
