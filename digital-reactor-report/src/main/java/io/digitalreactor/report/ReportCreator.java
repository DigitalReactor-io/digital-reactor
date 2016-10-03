package io.digitalreactor.report;

import io.digitalreactor.report.model.Credentials;

/**
 * Created by ingvard on 13.09.16.
 */
public interface ReportCreator <R> {
    R create(Credentials credentials);
}
