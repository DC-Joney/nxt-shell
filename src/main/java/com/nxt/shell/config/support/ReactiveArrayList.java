package com.nxt.shell.config.support;

import java.util.ArrayList;

public class ReactiveArrayList<E> {

    private ArrayList<E> delegate = new ArrayList<>();

    public ReactiveArrayList add(E e) {
        delegate.add(e);
        return this;
    }

    public ArrayList<E> toList() {
        return delegate;
    }

    public int size(){
        return delegate.size();
    }


    public void clear() {
        delegate.clear();
    }

}
