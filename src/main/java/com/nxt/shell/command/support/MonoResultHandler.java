package com.nxt.shell.command.support;

import org.reactivestreams.Subscription;
import org.springframework.shell.ResultHandler;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;


public class MonoResultHandler extends BaseSubscriber<Void> implements ResultHandler<Mono<Void>> {

    private List<ExceptionHandler> exceptionHandlers;

    public MonoResultHandler(List<ExceptionHandler> exceptionHandlers) {
        this.exceptionHandlers = exceptionHandlers;
    }

    @Override
    public void handleResult(Mono<Void> mono) {
        mono.doOnError(throwable -> exceptionHandlers.forEach(e -> e.handleException(throwable)))
                .subscribeOn(Schedulers.newParallel("Parallel-"))
                .subscribe(this);
    }


    @Override
    protected void hookOnSubscribe(Subscription subscription) {
        request(Integer.MAX_VALUE);
    }
}
