package ru.itk.spring_mvc.service;

import ru.itk.spring_mvc.model.Author;

public interface AuthorService {
    boolean existById(Long id);
    Author findAuthorById(Long id);
}
