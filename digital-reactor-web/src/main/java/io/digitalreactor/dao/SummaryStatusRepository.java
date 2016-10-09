package io.digitalreactor.dao;

import io.digitalreactor.model.SummaryStatus;
import io.digitalreactor.model.SummaryStatusEnum;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by MStepachev on 13.09.2016.
 */
public interface SummaryStatusRepository extends MongoRepository<SummaryStatus, String> {
    SummaryStatus findByAccountIdAndSiteId(String accountId, String siteId);
    SummaryStatus findByStatus(SummaryStatusEnum status);
    List<SummaryStatus> findAllByStatus(SummaryStatusEnum status);
}
