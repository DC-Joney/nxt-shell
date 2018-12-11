package com.nxt.shell.utils;

import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Optional;

public interface Predicates {

    static <T> PredicateBuilder capacity(int initialCapacity){
        return new PredicateBuilder(initialCapacity);
    }

    class PredicateBuilder<T>{
        private final Predications<T> predications;
        public PredicateBuilder(int initialCapacity){
            predications = new Predications<T>(initialCapacity);
        }

        PredicateBuilder add(T t, Object param){
            Optional.ofNullable(param)
                    .map(p-> t)
                    .map(this::add);
            return this;
        }

        PredicateBuilder add(T t){
            addData(t);
            return this;
        }

        void addData(T t){
            Assert.notNull(t,"");
            predications.add(t);
        }

        <E> E[] build(E[] t){
            return predications.toArray(t);
        }

    }

    public static class Predications<T> extends ArrayList<T> {
        public Predications(int initialCapacity) {
            super(initialCapacity);
        }
    }
}
