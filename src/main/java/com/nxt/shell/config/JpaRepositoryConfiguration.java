package com.nxt.shell.config;

import com.nxt.shell.config.annotation.EnableJpaReactiveRepository;
import com.nxt.shell.config.support.ReactiveOracleRepositoryConfigurationExtension;
import com.nxt.shell.config.support.ReactiveQueryCreationListener;
import com.nxt.shell.config.support.ReactiveRepositoryFactoryBean;
import com.nxt.shell.dao.impl.JpaRepositoryBaseImpl;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.ConditionalOnRepositoryType;
import org.springframework.boot.autoconfigure.data.RepositoryType;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Role;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.JpaRepositoryConfigExtension;

@Configuration
@EnableJpaReactiveRepository(repositoryBaseClass = JpaRepositoryBaseImpl.class
        , basePackages = "com.nxt.shell.dao",
        repositoryFactoryBeanClass = ReactiveRepositoryFactoryBean.class)
@ConditionalOnRepositoryType(store = "oracle", type = RepositoryType.IMPERATIVE)
public class JpaRepositoryConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public JpaRepositoryConfigExtension jpaRepositoryConfigExtension() {
        return new ReactiveOracleRepositoryConfigurationExtension();
    }

    @Bean
    @Role(BeanDefinition.ROLE_APPLICATION)
    public OracleEnvironment oracleEnvironment() {
        return new OracleEnvironment();
    }

    @Bean
    public ConverterSupport converterSupport(){
        return new ConverterSupport();
    }

    @Bean
    @DependsOn("converterSupport")
    public ReactiveQueryCreationListener reactiveQueryCreationListener(ConverterSupport converterSupport){
        return new ReactiveQueryCreationListener(converterSupport);
    }

    protected class OracleEnvironment implements EnvironmentAware {
        private Environment environment;
        @Override
        public void setEnvironment(Environment environment) {
            this.environment = environment;
        }
    }

}
