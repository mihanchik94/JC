package ru.itk.spring_mvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itk.spring_mvc.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
