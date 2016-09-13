package io.digitalreactor.dao;

import io.digitalreactor.model.Account;
import io.digitalreactor.model.SummaryStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by MStepachev on 13.09.2016.
 */
public interface SummaryStatusRepository extends MongoRepository<SummaryStatus, String> {

}
