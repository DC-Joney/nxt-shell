package com.nxt.shell.dao.base;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.repository.NoRepositoryBean;
import reactor.core.publisher.Flux;

import static com.nxt.shell.utils.JpaConfigUtils.HARD_DELETE_NOT_SUPPORTED;


@NoRepositoryBean
public interface ReactiveStreamRepository <T,ID>  extends JpaRepositoryImplementation<T,ID> {

    default Flux<T> findAllByFlux(Sort sort){
        return Flux.fromIterable(findAll(sort));
    }

    default Flux<T> findAllBySomeCondition(Specification<T> specification, Sort sort){
        return Flux.fromStream(findAll(specification, sort).parallelStream());
    }

    @Override
    default void deleteById(ID id) {
        throw new UnsupportedOperationException(HARD_DELETE_NOT_SUPPORTED);
    }

    @Override
    default void delete(T entity) {
        throw new UnsupportedOperationException(HARD_DELETE_NOT_SUPPORTED);
    }

    @Override
    default void deleteAll(Iterable<? extends T> entities) {
        throw new UnsupportedOperationException(HARD_DELETE_NOT_SUPPORTED);
    }

    @Override
    default void deleteAll() {
        throw new UnsupportedOperationException(HARD_DELETE_NOT_SUPPORTED);
    }

}
