package com.example.orm.jpa;

public interface UniqueIdGenerator<T> {
    T getNextUniqueId();
}
