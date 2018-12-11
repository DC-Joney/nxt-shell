package com.nxt.shell.config.support;

import org.springframework.data.jpa.repository.config.JpaRepositoryConfigExtension;
import org.springframework.data.repository.core.RepositoryMetadata;

public class ReactiveOracleRepositoryConfigurationExtension extends JpaRepositoryConfigExtension {

    @Override
    public String getModuleName() {
        return "Reactive Oracle";
    }

    @Override
    protected String getModulePrefix() {
        return "oracle";
    }

    @Override
    protected boolean useRepositoryConfiguration(RepositoryMetadata metadata) {
        return metadata.isReactiveRepository();
    }
}
