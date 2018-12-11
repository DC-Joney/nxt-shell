package com.nxt.shell.test;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;
import reactor.core.publisher.WorkQueueProcessor;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

@Log4j2
public class FluxTest {

    public static void main(String[] args) {
        Flux.range(1,20)
//                .delayElements(Duration.ofMillis(10))
                .handle((s,syncSink)->{
                    syncSink.next(s);
                })
                .doOnNext(log::info)
//                .subscribeOn(Schedulers.newElastic(""))
                .subscribe(log::info);

    }
}
