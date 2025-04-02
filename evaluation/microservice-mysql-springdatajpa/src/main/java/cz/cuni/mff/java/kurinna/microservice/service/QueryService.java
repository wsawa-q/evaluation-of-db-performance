package cz.cuni.mff.java.kurinna.microservice.service;

import cz.cuni.mff.java.kurinna.microservice.model.Customer;
import cz.cuni.mff.java.kurinna.microservice.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QueryService {
    private final CustomerRepository customerRepository;

    public QueryService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void deleteAll() {
        customerRepository.deleteAll();
    }

    public void deleteById(Long id) {
        customerRepository.deleteById(id);
    }

    public Customer save(Customer user) {
        return customerRepository.save(user);
    }

    public Customer findById(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }
}
