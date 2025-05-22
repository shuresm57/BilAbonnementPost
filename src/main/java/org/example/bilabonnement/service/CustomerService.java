package org.example.bilabonnement.service;


import org.example.bilabonnement.model.Customer;
import org.example.bilabonnement.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> fetchAll()
    {
        return customerRepository.fetchAll();
    }

    public void addCustomer(Customer customer)
    {
        customerRepository.addCustomer(customer);
    }

    public int getNextCustomerId() {
        return customerRepository.getNextCustomerId();
    }
}
