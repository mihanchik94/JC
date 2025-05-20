package ru.itk.stream.agregation;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@AllArgsConstructor
@Getter
public class Student {
    private String name;
    private Map<String, Integer> grades;
}
