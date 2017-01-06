package io.digitalreactor.web.contract.dto.report.direct;

import io.digitalreactor.web.contract.dto.report.ReportTypeEnum;

import java.util.List;

/**
 * Created by ingvard on 05.01.17.
 */
public class DirectSearchPhraseReportDto {
    private ReportTypeEnum type = ReportTypeEnum.SEARCH_PHRASE_YANDEX_DIRECT;
    private List<DirectSearchPhraseCutDto> reports;

    public DirectSearchPhraseReportDto(List<DirectSearchPhraseCutDto> reports) {
        this.reports = reports;
    }

    public List<DirectSearchPhraseCutDto> getReports() {
        return reports;
    }

    public ReportTypeEnum getType() {
        return type;
    }
}
