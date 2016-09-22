package io.digitalreactor.dao;

import io.digitalreactor.model.Account;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

/**
 * Created by MStepachev on 07.09.2016.
 */
public interface AccountRepository extends MongoRepository<Account, String> {
    Account findByEmail(String email);

    @Query(value = "{ 'id' : ?0, 'sites.id' : ?1 }", fields = "{ 'sites.yandexAccess' : 1 }")
    Account findByAccountIdAndSiteId(String id, String siteId);

    default Boolean existsByEmail(String email) {
        return findByEmail(email) != null;
    }
}
