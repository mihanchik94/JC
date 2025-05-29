package ru.itk.spring_mvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itk.spring_mvc.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
