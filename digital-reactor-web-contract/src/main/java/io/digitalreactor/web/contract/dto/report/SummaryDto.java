package io.digitalreactor.web.contract.dto.report;

import java.util.List;

/**
 * Created by MStepachev on 11.05.2016.
 */
public class SummaryDto {

    public SummaryDto(List<Object> reprots) {
        this.reprots = reprots;
    }

    private List<Object> reprots;

    public List<Object> getReprots() {
        return reprots;
    }

}
