package io.digitalreactor.report.builder;

/**
 * Created by ingvard on 13.09.16.
 */
public interface ReportBuilder<T, E> {
    T build(E input);
}
