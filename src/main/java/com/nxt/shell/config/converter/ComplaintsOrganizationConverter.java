package com.nxt.shell.config.converter;

import com.nxt.shell.dto.ComplaintsOrganizationDto;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.convert.converter.Converter;

import java.beans.PropertyDescriptor;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class ComplaintsOrganizationConverter implements Converter<Map<String, Object>, ComplaintsOrganizationDto> {

    private final MutableValues mutableValues;

    public ComplaintsOrganizationConverter() {

        this.mutableValues = new MutableValues(new BeanWrapperImpl(ComplaintsOrganizationDto.class).getPropertyDescriptors());

        this.mutableValues.afterPropertiesSet();
    }

    @Override
    public ComplaintsOrganizationDto convert(Map<String, Object> source) {
        BeanWrapper beanWrapper =  new BeanWrapperImpl(ComplaintsOrganizationDto.class);

        MutablePropertyValues mutablePropertyValues = new MutablePropertyValues();

        source.forEach((key, value) ->
                Optional.of(key)
                        .filter(mutableValues::contains)
                        .ifPresent(k -> mutablePropertyValues.add(mutableValues.getPropertyKey(k), value)));

        beanWrapper.setPropertyValues(mutablePropertyValues);

        return (ComplaintsOrganizationDto) beanWrapper.getWrappedInstance();
    }


    private static class MutableValues implements InitializingBean {

        private Map<String, String> propertyValues;

        private PropertyDescriptor[] descriptors;

        MutableValues(PropertyDescriptor[] descriptors) {
            this.descriptors = descriptors;
            this.propertyValues = new ConcurrentHashMap<>(10);
        }

        @Override
        public void afterPropertiesSet() {
            Arrays.stream(descriptors).forEach(s -> propertyValues.computeIfAbsent(s.getName().toLowerCase(), name -> s.getName()));
        }

        boolean contains(String propertyKey) {
            return propertyValues.containsKey(propertyKey.toLowerCase());
        }

        String getPropertyKey(String propertyKey) {
            return propertyValues.get(propertyKey.toLowerCase());
        }
    }

}
