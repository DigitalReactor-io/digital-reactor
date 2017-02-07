package io.digitalreactor.vendor.yandex.specification;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.digitalreactor.vendor.yandex.domain.CustomRequest;
import io.digitalreactor.vendor.yandex.model.PhraseRow;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by ingvard on 06.01.17.
 */
//TODO refactoring - make to a builder close to a select semantics
public class DirectSearchPhrasesWithGoalsRequest implements CustomRequest<String, List<PhraseRow>> {

    private final String QUERY_TEMPLATE = "/stat/v1/data" +
            "?ids=%s" +
            "&oauth_token=%s" +
            "&date1=%s" +
            "&date2=%s" +
            "&limit=10000&offset=1" +
            "&filters=ym:s:LastDirectClickOrder!n" +
            "&metrics=ym:s:visits,ym:s:bounceRate,ym:s:pageDepth,ym:s:avgVisitDurationSeconds%s" + // <-- goals
            "&dimensions=ym:s:lastDirectClickOrder,ym:s:lastDirectClickBanner,ym:s:lastDirectPhraseOrCond,ym:s:lastDirectSearchPhrase" +
            "&include_undefined=true";

    private final String GOAL_PARAMETER_TEMPLATE = "ym:s:goal%susers";

    private final String oauthToken;
    private final String counterId;
    private final String endIntervalDate;
    private final String startIntervalDate;
    private final List<String> goalsIds;

    public DirectSearchPhrasesWithGoalsRequest(String oauthToken, String counterId, LocalDate startIntervalDate, LocalDate endIntervalDate, List<String> goalsIds) {
        this(oauthToken, counterId, startIntervalDate.toString(), endIntervalDate.toString(), goalsIds);
    }

    public DirectSearchPhrasesWithGoalsRequest(String oauthToken, String counterId, String startIntervalDate, String endIntervalDate, List<String> goalsIds) {
        this.oauthToken = oauthToken;
        this.counterId = counterId;
        this.endIntervalDate = endIntervalDate;
        this.startIntervalDate = startIntervalDate;
        this.goalsIds = goalsIds;
    }

    private String makeListOfGoals(List<String> goalsIds) {
        return goalsIds.stream()
                .map(goalId -> String.format(GOAL_PARAMETER_TEMPLATE, goalId))
                .collect(Collectors.joining(","));
    }

    @Override
    public String toQuery() {
        String additionMetrics = "";

        if (goalsIds != null) {
            additionMetrics = "," + makeListOfGoals(goalsIds);
        }

        return String.format(QUERY_TEMPLATE, counterId, oauthToken, startIntervalDate, endIntervalDate, additionMetrics);
    }

    @Override
    public List<PhraseRow> transform(String input) {
        List<PhraseRow> phrases = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode root = mapper.readTree(input);
            JsonNode results = root.get("data");
            for (JsonNode row : results) {
                String searchPhrase = row.get("dimensions").get(3).get("name").asText();
                int visits = row.get("metrics").get(0).asInt();

                double bounceRate = row.get("metrics").get(1).asDouble();
                double pageDepth = row.get("metrics").get(2).asDouble();
                double avgVisitDurationSeconds = row.get("metrics").get(3).asDouble();

                Map<String, Integer> goalsAndConversion = new HashMap<>();

                if (row.get("metrics").size() > 4) {
                    for(int i = 4; i < row.get("metrics").size(); i++) {
                        String goal = root.get("query").get("metrics").get(i).asText();
                        int goalUsers = row.get("metrics").get(i).asInt();

                        goalsAndConversion.put(goal, goalUsers);
                    }
                }

                PhraseRow phrase = new PhraseRow(
                        searchPhrase,
                        visits,
                        bounceRate,
                        pageDepth,
                        avgVisitDurationSeconds,
                        goalsAndConversion
                );

                phrases.add(phrase);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return phrases;
    }
}
