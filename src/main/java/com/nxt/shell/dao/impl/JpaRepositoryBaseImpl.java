package com.nxt.shell.dao.impl;

import com.nxt.shell.dao.base.ReactiveStreamRepository;
import org.springframework.data.jpa.provider.PersistenceProvider;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;

public class JpaRepositoryBaseImpl<T, ID> extends SimpleJpaRepository<T, ID> implements ReactiveStreamRepository<T, ID> {

    private final JpaEntityInformation<T, ?> entityInformation;
    private final EntityManager em;
    private final PersistenceProvider provider;

    public JpaRepositoryBaseImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityInformation = entityInformation;
        this.em = entityManager;
        this.provider = PersistenceProvider.fromEntityManager(entityManager);
    }


//    @Override
//    public Flux<T> findAllByFlux(Pageable pageable) {
//
//        Pageable pageable1 = Optional.ofNullable(pageable)
//                .orElse(PageRequest.of(1, Long.bitCount(count())));
//
//
//        CriteriaBuilder builder = em.getCriteriaBuilder();
//        CriteriaQuery query = builder.createQuery(entityInformation.getClass());
//        Root root = query.from(entityInformation.getClass());
//        Predicate gt = builder.gt(root.get(""), 20);
//        query.where(gt);
//        TypedQuery typedQuery = em.createQuery(query);
////        Spliterators.
//        SetJoin setJoin = root.joinSet("", JoinType.LEFT);
//        Join join = root.join("", JoinType.LEFT);
//        query.select(builder.construct(Pageable.class,
//                root.get(""), root.get("")));
//        Selection selection = root.get("");
//        return Flux.fromStream(typedQuery.getResultStream());
//    }


}
