package io.digitalreactor.dao;

import io.digitalreactor.model.Account;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by MStepachev on 07.09.2016.
 */
public interface AccountRepository extends MongoRepository<Account, String> {
    Account findByEmail(String email);
}
