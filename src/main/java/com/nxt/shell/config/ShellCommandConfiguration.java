package com.nxt.shell.config;

import com.nxt.shell.command.support.ExceptionHandler;
import com.nxt.shell.command.support.MonoResultHandler;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.core.annotation.Order;

import java.util.List;

@Configuration
@AutoConfigureAfter(JpaRepositoryConfiguration.class)
@ConditionalOnClass({MonoResultHandler.class})
public class ShellCommandConfiguration {

    @Bean
    @Role(BeanDefinition.ROLE_APPLICATION)
    @Order(-1)
    public MonoResultHandler resultHandler(List<ExceptionHandler> fluxResultHandlers){
        return new MonoResultHandler(fluxResultHandlers);
    }
}
