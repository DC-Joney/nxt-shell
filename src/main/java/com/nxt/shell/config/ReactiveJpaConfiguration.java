package com.nxt.shell.config;

import com.nxt.shell.config.support.ReactiveRepositoryFactoryBean;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.core.support.QueryCreationListener;
import org.springframework.data.repository.core.support.RepositoryProxyPostProcessor;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Log4j2
@Configuration
@AutoConfigureAfter(JpaRepositoryConfiguration.class)
public class ReactiveJpaConfiguration  implements InitializingBean {

    private List<ReactiveRepositoryFactoryBean> factoryBeanSupports;
    private List<RepositoryProxyPostProcessor> postProcessors;
    private List<QueryCreationListener> queryCreationListeners;

    public ReactiveJpaConfiguration(ObjectProvider<List<ReactiveRepositoryFactoryBean>> factoryBeanSupports,
                                    ObjectProvider<List<RepositoryProxyPostProcessor>> postProcessorPorvider,
                                    ObjectProvider<List<QueryCreationListener>> queryCreationListeners) {
        this.factoryBeanSupports = ofNullable(factoryBeanSupports.getIfAvailable());
        this.postProcessors = ofNullable(postProcessorPorvider.getIfAvailable());
        this.queryCreationListeners = ofNullable(queryCreationListeners.getIfAvailable());
    }


    @Override
    public void afterPropertiesSet(){
        factoryBeanSupports.forEach(this::initRepositoryFactoryBean);
    }


    private void initRepositoryFactoryBean(ReactiveRepositoryFactoryBean factoryBeanSupport){
        Optional.of(factoryBeanSupport)
                .filter(ReactiveRepositoryFactoryBean::isInitRepositoryFactoryBean)
                .flatMap(ReactiveRepositoryFactoryBean::getRepositoryFactorySupport)
                .ifPresent(f -> {
                    postProcessors.forEach(f::addRepositoryProxyPostProcessor);
                    queryCreationListeners.forEach(f::addQueryCreationListener);
                });
    }

    private <E> List<E> ofNullable(List<E> list){
        return Optional.ofNullable(list).orElse(Collections.emptyList());
    }

}
