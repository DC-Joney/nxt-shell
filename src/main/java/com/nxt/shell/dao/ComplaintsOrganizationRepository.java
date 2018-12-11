package com.nxt.shell.dao;

import com.nxt.shell.dao.base.ReactiveStreamRepository;
import com.nxt.shell.dto.ComplaintsOrganizationDto;
import com.nxt.shell.model.ComplaintsOrganization;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComplaintsOrganizationRepository extends ReactiveStreamRepository<ComplaintsOrganization,String> {

    @Query(value = "SELECT  TG.* ,AM.KINDNAME AS areaName  " +
            "FROM ( SELECT * " +
            "      FROM CMS_TSJG TG " +
            "      WHERE TG.DELETED = 0 " +
            "        AND TG.STRUTS = 1 " +
            "        AND TG.ADDRESS IN ?1 ) TG " +
            "       LEFT JOIN AREA_MANAGE AM ON AM.KINDID = TG.ADDRESS " +
            "ORDER BY AM.PX DESC",nativeQuery = true)
    List<ComplaintsOrganizationDto> findByAddressId(List<String> areadIds);


    @Query(value = " FROM ComplaintsOrganization tsjg where tsjg.deleted = 0 and tsjg.struts = 1 and tsjg.areaManage.id = :areaId")
    List<ComplaintsOrganization> findByAreaId(@Param("areaId") String areaId);
}
