package io.digitalreactor.vendor.yandex;

/**
 * Created by ingvard on 06.01.17.
 */
public interface DataTransformer<I, R> {
    R transform(I input);
}
