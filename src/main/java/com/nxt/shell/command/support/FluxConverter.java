package com.nxt.shell.command.support;

import com.nxt.shell.model.AreaManage;
import com.nxt.shell.service.ExportExcelService;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Log4j2
@Component
public  class FluxConverter<T extends AreaManage> implements Converter<String, Flux<T>> {

    private ExportExcelService<T> exportExcelService;

    public FluxConverter(ExportExcelService<T> exportExcelService){
        this.exportExcelService = exportExcelService;
    }

    @Override
    public Flux<T> convert(String source) {
        return exportExcelService.mergeExcel();
    }
}
