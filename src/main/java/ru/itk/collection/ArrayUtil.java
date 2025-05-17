package ru.itk.collection;

import java.lang.reflect.Array;

public class ArrayUtil {

    @SuppressWarnings("unchecked")
    public static <T> T[] filter(T[] array, Filter<T> filter) {
        T[] result = (T[]) Array.newInstance(array.getClass().getComponentType(), array.length);
        for (int index = 0; index < array.length; index++) {
            result[index] = filter.apply(array[index]);
        }
        return result;
    }
}
