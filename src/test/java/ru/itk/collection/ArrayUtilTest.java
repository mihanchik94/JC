package ru.itk.collection;

import org.junit.jupiter.api.Test;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ArrayUtilTest {
    @Test
    void whenCountElementsWithEmptyArrayThenEmptyMap() {
        Integer[] elements = {};
        Map<Integer, Long> expected = Map.of();
        assertEquals(expected, ArrayUtil.countElements(elements));
    }

    @Test
    void whenCountElementsWithSingleElementThenMapWithSingleElement() {
        String[] elements = {"apple"};
        Map<String, Long> expected = Map.of("apple", 1L);
        assertEquals(expected, ArrayUtil.countElements(elements));
    }

    @Test
    void whenCountElementsWithMultipleElementsThenMultipleElementsMap() {
        Integer[] elements = {1, 2, 2, 3, 3, 3};
        Map<Integer, Long> expected = Map.of(
                1, 1L,
                2, 2L,
                3, 3L
        );
        Map<Integer, Long> actual = ArrayUtil.countElements(elements);
        assertEquals(expected.size(), actual.size());
        assertEquals(expected.get(1), actual.get(1));
        assertEquals(expected.get(2), actual.get(2));
        assertEquals(expected.get(3), actual.get(3));
    }

    @Test
    void whenCountElementsWithAllSameElementsThenMapWithOnePair() {
        Double[] elements = {1.0, 1.0, 1.0, 1.0};
        Map<Double, Long> expected = Map.of(1.0, 4L);
        assertEquals(expected, ArrayUtil.countElements(elements));
    }
}