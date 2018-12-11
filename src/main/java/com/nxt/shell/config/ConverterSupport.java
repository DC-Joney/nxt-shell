package com.nxt.shell.config;

import com.nxt.shell.config.converter.ComplaintsOrganizationConverter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.core.convert.converter.ConverterRegistry;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.core.convert.support.DefaultConversionService;

import java.util.Optional;


@ConditionalOnClass
public class ConverterSupport  implements InitializingBean,ConverterRegistry{

    private Optional<ConverterRegistry> converterRegistry = Optional.empty();

    @Override
    public void afterPropertiesSet() {
       converterRegistry = Optional.ofNullable(DefaultConversionService.getSharedInstance())
                .filter(conversionService -> conversionService instanceof ConverterRegistry)
                .map(conversionService -> (ConverterRegistry) conversionService);
    }

    public void addDefaultConverter(){
        converterRegistry.ifPresent(registry->
                registry.addConverter(new ComplaintsOrganizationConverter()));
    }

    @Override
    public void addConverter(Converter<?, ?> converter) {
        converterRegistry.ifPresent(c-> c.addConverter(converter));
    }

    @Override
    public <S, T> void addConverter(Class<S> sourceType, Class<T> targetType, Converter<? super S, ? extends T> converter) {
        converterRegistry.ifPresent(c-> c.addConverter(sourceType,targetType,converter));
    }

    @Override
    public void addConverter(GenericConverter converter) {
        converterRegistry.ifPresent(c-> c.addConverter(converter));
    }

    @Override
    public void addConverterFactory(ConverterFactory<?, ?> factory) {
        converterRegistry.ifPresent(c-> c.addConverterFactory(factory));
    }

    @Override
    public void removeConvertible(Class<?> sourceType, Class<?> targetType) {
        converterRegistry.ifPresent(c-> c.removeConvertible(sourceType,targetType));
    }
}
