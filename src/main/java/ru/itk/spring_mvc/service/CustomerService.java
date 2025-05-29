package ru.itk.spring_mvc.service;

import ru.itk.spring_mvc.model.Customer;

public interface CustomerService {
    Customer findCustomerById(Long id);
}
