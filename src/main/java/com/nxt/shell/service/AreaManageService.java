package com.nxt.shell.service;

import reactor.core.publisher.Flux;

public interface AreaManageService<T> {

    Flux<T> findAllAreaManages();

    Flux<T> findAreaManageByParentId(T parentId);
}
