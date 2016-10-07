package io.digitalreactor.web.contract;

import io.digitalreactor.web.contract.dto.LogUI;

import java.util.List;

/**
 * Created by MStepachev on 07.10.2016.
 */
public interface LogManagementWebServiceContract {
    String WEB_SERVICE_PATH = "management";
    String LOGS_PATH = "logs";

    public List<LogUI> getLogs(String level);
}
