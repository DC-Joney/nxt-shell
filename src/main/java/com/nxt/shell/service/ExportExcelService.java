package com.nxt.shell.service;

import reactor.core.publisher.Flux;

public interface ExportExcelService<T> {
     Flux<T> mergeExcel();
}
