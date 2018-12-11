package com.nxt.shell.config.support;

import com.nxt.shell.config.annotation.EnableJpaReactiveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.AbstractRepositoryConfigurationSourceSupport;
import org.springframework.core.env.Environment;
import org.springframework.data.repository.config.BootstrapMode;
import org.springframework.data.repository.config.RepositoryConfigurationExtension;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.util.Locale;
import java.util.Optional;

public class ReactiveRepositoriesAutoConfigureRegistrar extends AbstractRepositoryConfigurationSourceSupport {
    private BootstrapMode bootstrapMode = null;

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
    protected Class<?> getConfiguration() {
        return ReactiveRepositoriesAutoConfigureRegistrar.EnableReactiveRepositoriesConfiguration.class;
    }

    @Override
    protected RepositoryConfigurationExtension getRepositoryConfigurationExtension() {
        return Optional.ofNullable(configurationExtension)
            .orElse(new ReactiveOracleRepositoryConfigurationExtension());
    }

    @Override
    protected BootstrapMode getBootstrapMode() {
        return (this.bootstrapMode == null) ? super.getBootstrapMode()
                : this.bootstrapMode;
    }

    @Override
    public void setEnvironment(Environment environment) {
        super.setEnvironment(environment);
        configureBootstrapMode(environment);
    }

    private void configureBootstrapMode(Environment environment) {
        String property = environment
                .getProperty("spring.data.jpa.repositories.bootstrap-mode");
        if (StringUtils.hasText(property)) {
            this.bootstrapMode = BootstrapMode
                    .valueOf(property.toUpperCase(Locale.ENGLISH));
        }
    }

    @EnableJpaReactiveRepository
    private static class EnableReactiveRepositoriesConfiguration {

    }
}
