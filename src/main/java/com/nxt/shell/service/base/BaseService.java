package com.nxt.shell.service.base;

import com.nxt.shell.dao.base.ReactiveStreamRepository;

public interface BaseService<T,ID>{
    ReactiveStreamRepository  getRepository();
}
