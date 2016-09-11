package io.digitalreactor.core.service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by ingvard on 08.09.16.
 */
public class TemporalTokenStorage {
    private Map<String, String> tokenStore = new ConcurrentHashMap<>();

    public String store(String token) {
        String keyStore = UUID.randomUUID().toString();
        tokenStore.put(keyStore, token);

        return keyStore;
    }

    public String poll(String keyStore) {
        return tokenStore.remove(keyStore);
    }

    public String get(String keyStore) {
        return tokenStore.get(keyStore);
    }
}
