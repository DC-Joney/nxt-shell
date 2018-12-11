package com.nxt.shell.command;


import com.nxt.shell.dao.ComplaintsOrganizationRepository;
import com.nxt.shell.dto.ComplaintsDto;
import com.nxt.shell.model.AreaManage;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.function.Tuple2;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;


@Log4j2
@ShellComponent
@ShellCommandGroup(value = "Export Excel")
public class ExportExcelCommand {

    @Autowired
    private ComplaintsOrganizationRepository repository;

    @ShellMethod(value = "Add two integers together.", key = "sum")
    public int add(@ShellOption("--app") int a, int b) {
        return a + b;
    }

    @ShellMethod("export command")
    public Mono<Void> export(@ShellOption(defaultValue = "0") Flux<Tuple2<String, Collection<ComplaintsDto>>> options) {
        return options.doOnNext(tuple2 -> log.info(tuple2.getT1() + " : " + tuple2.getT2().size()))
                .count()
                .doOnNext(log::info)
                .then();
    }

    @Transactional
    @ShellMethod("export command")
    public void test(@ShellOption(defaultValue = "0") String ops) {
        repository.findByAddressId(Arrays.asList("")).forEach(s -> log.info(s.getAreaName()));
    }
}
