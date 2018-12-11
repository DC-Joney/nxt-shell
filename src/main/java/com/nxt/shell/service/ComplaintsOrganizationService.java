package com.nxt.shell.service;

import com.nxt.shell.dto.ComplaintsOrganizationDto;
import com.nxt.shell.model.AreaManage;
import com.nxt.shell.model.ComplaintsOrganization;
import com.nxt.shell.service.base.BaseService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface ComplaintsOrganizationService<T> extends BaseService<T,String> {
    <E extends AreaManage> Mono<Tuple2<E,List<ComplaintsOrganizationDto>>> findAllComplaintOrganization(E areaManage, Function<? super E,? extends Flux<E>> fluxFunction );

    Optional<List<T>> findByAreaId(AreaManage areaManage);
}
