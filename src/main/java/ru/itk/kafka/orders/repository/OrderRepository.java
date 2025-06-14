package ru.itk.kafka.orders.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itk.kafka.orders.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
