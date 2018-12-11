package com.nxt.shell.config.support;


import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.AnyNestedCondition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.EntityManagerFactoryBuilderCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.task.TaskExecutionAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Import;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.JpaRepositoryConfigExtension;



@ConditionalOnClass(JpaRepository.class)
@ConditionalOnProperty(prefix = "spring.data.jpa.repositories", name = "enabled", havingValue = "true", matchIfMissing = true)
@Import(ReactiveRepositoriesAutoConfigureRegistrar.class)
@AutoConfigureBefore(JpaRepositoriesAutoConfiguration.class)
@AutoConfigureAfter({HibernateJpaAutoConfiguration.class,
        TaskExecutionAutoConfiguration.class})
public class ReactiveRepositoriesAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public JpaRepositoryConfigExtension jpaRepositoryConfigExtension(){
        return new ReactiveOracleRepositoryConfigurationExtension();
    }


    @Bean
    @Conditional(ReactiveRepositoriesAutoConfiguration.BootstrapExecutorCondition.class)
    public EntityManagerFactoryBuilderCustomizer entityManagerFactoryBootstrapExecutorCustomizer(
            ObjectProvider<AsyncTaskExecutor> taskExecutor) {
        return (builder) -> builder.setBootstrapExecutor(taskExecutor.getIfAvailable());
    }

    private static final class BootstrapExecutorCondition extends AnyNestedCondition {

        BootstrapExecutorCondition() {
            super(ConfigurationPhase.REGISTER_BEAN);
        }

        @ConditionalOnProperty(prefix = "spring.data.jpa.repositories", name = "bootstrap-mode", havingValue = "deferred", matchIfMissing = false)
        static class DeferredBootstrapMode {

        }

        @ConditionalOnProperty(prefix = "spring.data.jpa.repositories", name = "bootstrap-mode", havingValue = "lazy", matchIfMissing = false)
        static class LazyBootstrapMode {

        }

    }

}

