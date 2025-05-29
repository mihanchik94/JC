package ru.itk.spring_mvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itk.spring_mvc.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
