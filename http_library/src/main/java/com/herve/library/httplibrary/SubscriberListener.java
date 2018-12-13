package com.herve.library.httplibrary;

public interface SubscriberListener<T> {
    void onNext(T t) throws Exception;

    void onError(Throwable throwable);

}
