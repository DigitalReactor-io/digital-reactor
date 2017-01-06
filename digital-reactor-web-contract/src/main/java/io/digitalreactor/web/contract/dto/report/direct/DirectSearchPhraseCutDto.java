package io.digitalreactor.web.contract.dto.report.direct;

import java.util.List;

/**
 * Created by ingvard on 05.01.17.
 */
public class DirectSearchPhraseCutDto {
    private String goalName;
    private DSPCutType type;
    private List<DirectSearchPhraseDto> successPhrases;
    private List<DirectSearchPhraseDto> badPhrases;

    public DirectSearchPhraseCutDto(String goalName, DSPCutType type, List<DirectSearchPhraseDto> successPhrases, List<DirectSearchPhraseDto> badPhrases) {
        this.goalName = goalName;
        this.type = type;
        this.successPhrases = successPhrases;
        this.badPhrases = badPhrases;
    }

    public String getGoalName() {
        return goalName;
    }

    public DSPCutType getType() {
        return type;
    }

    public List<DirectSearchPhraseDto> getSuccessPhrases() {
        return successPhrases;
    }

    public List<DirectSearchPhraseDto> getBadPhrases() {
        return badPhrases;
    }
}
