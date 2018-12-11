package com.nxt.shell.config.annotation;

import com.nxt.shell.config.support.ReactiveOracleRepositoryConfigurationExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.config.RepositoryBeanDefinitionRegistrarSupport;
import org.springframework.data.repository.config.RepositoryConfigurationExtension;

import java.lang.annotation.Annotation;
import java.util.Optional;

public class ReactiveRepositoryRegistrar extends RepositoryBeanDefinitionRegistrarSupport {

    private RepositoryConfigurationExtension configurationExtension;


    @Autowired(required = false)
    public void setConfigurationExtension(RepositoryConfigurationExtension configurationExtension) {
        this.configurationExtension = configurationExtension;
    }

    @Override
    protected Class<? extends Annotation> getAnnotation() {
        return EnableJpaReactiveRepository.class;
    }

    @Override
    protected RepositoryConfigurationExtension getExtension() {
        return Optional.ofNullable(configurationExtension)
                .orElse(new ReactiveOracleRepositoryConfigurationExtension());
    }
}
