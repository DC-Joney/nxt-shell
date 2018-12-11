package com.nxt.shell.config.support;

import com.nxt.shell.config.ex.NotInitializedException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.OrderComparator;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.core.support.QueryCreationListener;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Log4j2
@SuppressWarnings("unchecked")
public class ReactiveRepositoryFactoryBean extends JpaRepositoryFactoryBean implements ApplicationContextAware {

    private RepositoryFactorySupport repositoryFactorySupport;

    private AtomicBoolean initInstance = new AtomicBoolean(false);

    private ApplicationContext applicationContext;

    public ReactiveRepositoryFactoryBean(Class repositoryInterface) {
        super(repositoryInterface);
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }


    @Override
    protected RepositoryFactorySupport createRepositoryFactory(EntityManager entityManager) {
        repositoryFactorySupport = super.createRepositoryFactory(entityManager);

        Optional.of(applicationContext)
//                .filter(a-> a.eve)
                .map(context -> BeanFactoryUtils.beansOfTypeIncludingAncestors(
                        context, QueryCreationListener.class, true, false).values())
                .orElseGet(Collections::emptyList)
                .stream()
                .sorted(OrderComparator.INSTANCE)
                .forEach(repositoryFactorySupport::addQueryCreationListener);

        initInstance.set(true);
        return repositoryFactorySupport;
    }

    public Optional<RepositoryFactorySupport> getRepositoryFactorySupport() {
        return Optional.ofNullable(repositoryFactorySupport);
    }

    public boolean isInitRepositoryFactoryBean() {
        return Optional.of(initInstance.get()).filter(s -> s)
                .orElseThrow(() -> {
                    log.warn("The RepositoryFactorySupport is not init");
                    return new NotInitializedException("The RepositoryFactorySupport is not init");
                });
    }

}
