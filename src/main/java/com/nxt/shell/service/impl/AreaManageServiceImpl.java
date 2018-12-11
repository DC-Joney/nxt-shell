package com.nxt.shell.service.impl;

import com.nxt.shell.dao.AreaManageRepository;
import com.nxt.shell.model.AreaManage;
import com.nxt.shell.service.AreaManageService;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class AreaManageServiceImpl implements AreaManageService<AreaManage> {

    private AreaManageRepository repository;

    public AreaManageServiceImpl(AreaManageRepository repository) {
        this.repository = repository;
    }

    @Override
    public Flux<AreaManage> findAllAreaManages() {
        return repository.findAllBySomeCondition((root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(builder.equal(root.get("deleted"), 0));
            predicates.add(root.get("id").in("2c9081851bd3f784011bd4fd5d0c05aa").not());
            predicates.add(builder.equal(root.get("parentId"), "0"));
            predicates.add(builder.or(
                    builder.lt(root.get("px"), 55),
                    builder.gt(root.get("px"), 199)
            ));
            predicates.add(builder.gt(root.get("px"), 13));
            return query.where(predicates.toArray(new Predicate[0])).getRestriction();
        }, Sort.by(Sort.Direction.DESC, "px"));
    }

    @Override
    public Flux<AreaManage> findAreaManageByParentId(AreaManage areaManage) {
        return Mono.justOrEmpty(areaManage)
                .map(AreaManage::getId)
                .flatMapIterable(repository::findAreaManageByParentId);
    }
}
