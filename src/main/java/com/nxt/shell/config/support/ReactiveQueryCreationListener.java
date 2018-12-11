package com.nxt.shell.config.support;

import com.nxt.shell.config.ConverterSupport;
import com.nxt.shell.dto.ComplaintsOrganizationDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.repository.core.support.QueryCreationListener;
import org.springframework.data.repository.query.RepositoryQuery;

import java.util.Optional;


@Log4j2
public class ReactiveQueryCreationListener implements QueryCreationListener<RepositoryQuery> {

    private ConverterSupport converterSupport;

    public ReactiveQueryCreationListener(ConverterSupport converterSupport){
        this.converterSupport = converterSupport;
    }

    @Override
    public void onCreation(RepositoryQuery repositoryQuery) {
        Optional.ofNullable(repositoryQuery.getQueryMethod())
                .map(queryMethod -> queryMethod.getResultProcessor().getReturnedType())
                .filter(returnedType -> returnedType.getReturnedType().isAssignableFrom(ComplaintsOrganizationDto.class))
                .ifPresent(s-> converterSupport.addDefaultConverter());
    }
}
