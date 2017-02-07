package io.digitalreactor.report.builder;

import io.digitalreactor.vendor.yandex.model.Goal;
import io.digitalreactor.vendor.yandex.model.GoalResponse;
import io.digitalreactor.vendor.yandex.model.PhraseRow;
import io.digitalreactor.web.contract.dto.report.direct.DSPCutType;
import io.digitalreactor.web.contract.dto.report.direct.DirectSearchPhraseCutDto;
import io.digitalreactor.web.contract.dto.report.direct.DirectSearchPhraseDto;
import io.digitalreactor.web.contract.dto.report.direct.DirectSearchPhraseReportDto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static java.util.Comparator.comparing;

/**
 * Created by ingvard on 13.09.16.
 */
public class DirectSearchPhraseReportBuilder {
    public DirectSearchPhraseReportDto build(List<PhraseRow> phrases, GoalResponse goals) {

        List<DirectSearchPhraseDto> badPhrases = phrases.stream()
                .filter(this::badCondition)
                .map(
                        p -> {
                            return new DirectSearchPhraseDto(
                                    p.getSearchPhrase(),
                                    p.getVisits(),
                                    p.getBounceRate(),
                                    p.getPageDepth(),
                                    p.getAvgVisitDurationSeconds(),
                                    0
                            );
                        }
                )
                .sorted(comparing(DirectSearchPhraseDto::getVisits).reversed())
                .limit(10)
                .collect(Collectors.toList());

        List<DirectSearchPhraseDto> successPhrases = phrases.stream()
                .filter(this::successCondition)
                .map(
                        p -> {
                            return new DirectSearchPhraseDto(
                                    p.getSearchPhrase(),
                                    p.getVisits(),
                                    p.getBounceRate(),
                                    p.getPageDepth(),
                                    p.getAvgVisitDurationSeconds(),
                                    0
                            );
                        }
                )
                .sorted(comparing(DirectSearchPhraseDto::getVisits).reversed())
                .limit(10)
                .collect(Collectors.toList());

        List<DirectSearchPhraseCutDto> cuts = new ArrayList<>(Collections.singletonList(new DirectSearchPhraseCutDto("System test goal", DSPCutType.SYSTEM, successPhrases, badPhrases)));
        cuts.addAll(cutsByGoals(phrases, goals));

        return new DirectSearchPhraseReportDto(cuts);
    }

    private List<DirectSearchPhraseCutDto> cutsByGoals(List<PhraseRow> phrases, GoalResponse goals) {
        List<DirectSearchPhraseCutDto> cuts = new ArrayList<>();

        for (Goal goal : goals.getGoals()) {

            List<DirectSearchPhraseDto> badPhrases = phrases.stream()
                    .filter(p -> badConditionWithGoal(p, goal.getId()))
                    .map(
                            p -> {
                                return new DirectSearchPhraseDto(
                                        p.getSearchPhrase(),
                                        p.getVisits(),
                                        p.getBounceRate(),
                                        p.getPageDepth(),
                                        p.getAvgVisitDurationSeconds(),
                                        conversion(p, goal.getId())
                                );
                            }
                    )
                    .sorted(comparing(DirectSearchPhraseDto::getVisits).reversed())
                    .limit(10)
                    .collect(Collectors.toList());

            List<DirectSearchPhraseDto> successPhrases = phrases.stream()
                    .filter(p -> successCondition(p) && !badConditionWithGoal(p, goal.getId()))
                    .map(
                            p -> {
                                return new DirectSearchPhraseDto(
                                        p.getSearchPhrase(),
                                        p.getVisits(),
                                        p.getBounceRate(),
                                        p.getPageDepth(),
                                        p.getAvgVisitDurationSeconds(),
                                        conversion(p, goal.getId())
                                );
                            }
                    )
                    .sorted(comparing(DirectSearchPhraseDto::getQuality).reversed())
                    .limit(10)
                    .collect(Collectors.toList());

            cuts.add(new DirectSearchPhraseCutDto(goal.getName(), DSPCutType.GOAL, successPhrases, badPhrases));
        }

        return cuts;
    }

    private boolean badCondition(PhraseRow phrase) {
        return phrase.getBounceRate() >= 50.0 || phrase.getPageDepth() < 2.0 || phrase.getAvgVisitDurationSeconds() < 15;
    }

    private boolean badConditionWithGoal(PhraseRow phrase, int goalId) {

        return conversion(phrase, goalId) < 1;
    }

    private boolean successCondition(PhraseRow phrase) {
        return phrase.getBounceRate() < 50.0 && phrase.getAvgVisitDurationSeconds() > 15;
    }

    private double conversion(PhraseRow phrase, int goalId) {
        //todo fix it
        int countUsers = phrase.getGoalAndConversion().get(String.format("ym:s:goal%susers", goalId));

        return phrase.getVisits() == 0 ? 0 : ((double) countUsers) / ((double) phrase.getVisits());
    }
}
