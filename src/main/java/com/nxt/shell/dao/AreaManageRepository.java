package com.nxt.shell.dao;

import com.nxt.shell.dao.base.ReactiveStreamRepository;
import com.nxt.shell.model.AreaManage;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.Stream;

@Repository
public interface AreaManageRepository extends ReactiveStreamRepository<AreaManage,String> {


    @Transactional(readOnly = true)
    @Query("FROM AreaManage a where  a.parentId = :parentId  order by a.px desc")
    List<AreaManage> findAreaManageByParentId(@Param("parentId") String parentId);


    @Query("FROM AreaManage a where a.deleted = 0 and a.id = :kindId and (a.px < 55 or a.px>199)")
    AreaManage findAreaManageById(@Param("kindId") String kindId);


    default Stream<AreaManage> findAllParentId(String parentId){
        return findAreaManageByParentId(parentId).stream();
    }

}
