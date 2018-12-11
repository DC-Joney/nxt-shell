package com.nxt.shell.service.impl;

import com.nxt.shell.dto.ComplaintsDto;
import com.nxt.shell.model.AreaManage;
import com.nxt.shell.model.ComplaintsOrganization;
import com.nxt.shell.service.AreaManageService;
import com.nxt.shell.service.ComplaintsOrganizationService;
import com.nxt.shell.service.ExportExcelService;
import com.nxt.shell.utils.ComplaintsDtoUtils;
import com.nxt.shell.utils.ExportExcelUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.WorkQueueProcessor;
import reactor.core.scheduler.Schedulers;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.util.List;

@Log4j2
@Service
public class ExportExcelServiceImpl<E extends ComplaintsDto> implements ExportExcelService<Tuple2<String, List<ComplaintsDto>>> {

    @Autowired
    private AreaManageService<AreaManage> areaManageService;

    @Autowired
    private ComplaintsOrganizationService<ComplaintsOrganization> organizationService;

    @Override
    public Flux<Tuple2<String, List<ComplaintsDto>>> mergeExcel() {
        return WorkQueueProcessor.from(areaManageService.findAllAreaManages())
                .publishOn(Schedulers.newElastic("Thread"))
                .flatMap(this::mergeWith)
                .filter(tuple -> tuple.getT2().size() > 0)
                .publishOn(Schedulers.parallel())
                .handle((tuple2, synchronousSink) -> synchronousSink.next(ExportExcelUtils.exportExcel(tuple2)));
    }

    private Mono<Tuple2<String, List<ComplaintsDto>>> mergeWith(AreaManage areaManage) {
        ComplaintsDtoUtils.ComplaintsBuilder builder = ComplaintsDtoUtils.builder(20);
        return Mono.justOrEmpty(areaManage)
                .flatMapMany(areaManage1 -> {
                    organizationService.findByAreaId(areaManage1)
                            .ifPresent(organizations -> organizations.forEach(organization -> builder.parseAreaToDto(organization, areaManage1.getName())));
                    return areaManageService.findAreaManageByParentId(areaManage);
                })
                .filter(s -> !s.getDes().equals("1"))
                .<AreaManage>handle((areaManage1, synchronousSink) -> {
                    organizationService.findByAreaId(areaManage1)
                            .ifPresent(organizations -> organizations.forEach(organization ->
                                    builder.parseAreaToDto(organization, areaManage.getName(), areaManage1.getName())));
                    synchronousSink.next(areaManage1);
                })
                .flatMap(areaManage1 ->  organizationService.findAllComplaintOrganization(areaManage1, areaManageService::findAreaManageByParentId))
                .reduce(builder,(build,tuple2)->{
                    tuple2.getT2().forEach(dto -> builder.parseAreaToDto(dto, areaManage.getName(), tuple2.getT1().getName(), dto.getAreaName()));
                    return builder;
                })
                .map(build -> Tuples.of(areaManage.getName(), build.buildDtoCollection()));
    }
}
