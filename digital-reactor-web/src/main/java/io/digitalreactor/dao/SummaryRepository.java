package io.digitalreactor.dao;

import io.digitalreactor.web.contract.dto.report.Summary;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by ingvard on 24.09.16.
 */
public interface SummaryRepository extends MongoRepository<Summary, String> {
    Summary findByTaskId(String taskId);
}
