package ru.itk.collection;

public interface Filter<T> {
    T apply(T o);
}
