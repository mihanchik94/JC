package ru.itk.collection;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArrayUtilTest {

    @Test
    void whenFilterIntegerArrayThenFilteredIntegerArray() {
        Integer[] intArray = {1, 2, 3, 4};
        Filter<Integer> sumTwoFilter = num -> num + 2;
        Integer[] expected = {3, 4, 5, 6};

        Integer[] actual = ArrayUtil.filter(intArray, sumTwoFilter);

        assertArrayEquals(expected, actual);

    }

    @Test
    void whenFilterStringArrayThenFilteredStringArray() {
        String[] strArray = {"1", "2", "3", "4"};
        Filter<String> addZeroStringFilter = str -> str + "0";
        String[] expected = {"10", "20", "30", "40"};

        String[] actual = ArrayUtil.filter(strArray, addZeroStringFilter);

        assertArrayEquals(expected, actual);
    }
}