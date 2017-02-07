package io.digitalreactor.vendor.yandex.domain;

import io.digitalreactor.vendor.yandex.DataTransformer;

/**
 * Created by ingvard on 06.01.17.
 */
public interface CustomRequest<I, R> extends DataTransformer<I, R>, Request {
}
