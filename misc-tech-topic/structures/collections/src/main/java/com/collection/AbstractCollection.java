package com.collection;

import java.util.List;

abstract class AbstractCollection<T> implements List<T> {

    AbstractCollection() {
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }
}
