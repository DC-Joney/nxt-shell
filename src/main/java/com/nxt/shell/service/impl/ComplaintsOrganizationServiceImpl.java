package com.nxt.shell.service.impl;

import com.nxt.shell.config.support.ReactiveArrayList;
import com.nxt.shell.dao.ComplaintsOrganizationRepository;
import com.nxt.shell.dao.base.ReactiveStreamRepository;
import com.nxt.shell.dto.ComplaintsOrganizationDto;
import com.nxt.shell.model.AreaManage;
import com.nxt.shell.model.ComplaintsOrganization;
import com.nxt.shell.service.ComplaintsOrganizationService;
import com.sun.org.apache.regexp.internal.RE;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.stream.Collectors;

@Log4j2
@Service
public class ComplaintsOrganizationServiceImpl implements ComplaintsOrganizationService<ComplaintsOrganization> {

    private ComplaintsOrganizationRepository repository;

    public ComplaintsOrganizationServiceImpl(ComplaintsOrganizationRepository repository) {
        this.repository = repository;
    }

    @Override
    public <E extends AreaManage> Mono<Tuple2<E,List<ComplaintsOrganizationDto>>> findAllComplaintOrganization(E areaManage, Function<? super E, ? extends Flux<E>> fluxFunction) {
        return fluxFunction.apply(areaManage)
                .map(AreaManage::getId)
                .reduceWith(ReactiveArrayList<String>::new, ReactiveArrayList::add)
                .filter(s -> s.size() > 0)
                .map(list-> Tuples.of(areaManage,repository.findByAddressId(list.toList())));
    }


    @Override
    public Optional<List<ComplaintsOrganization>> findByAreaId(AreaManage areaManage) {
        return Optional.ofNullable(repository.findByAreaId(areaManage.getId()));
    }

    @Override
    public ReactiveStreamRepository getRepository() {
        return repository;
    }




}
