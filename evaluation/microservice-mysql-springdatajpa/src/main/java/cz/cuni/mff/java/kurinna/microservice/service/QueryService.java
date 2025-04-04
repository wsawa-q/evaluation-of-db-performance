package cz.cuni.mff.java.kurinna.microservice.service;

import cz.cuni.mff.java.kurinna.microservice.dto.PricingSummary;
import cz.cuni.mff.java.kurinna.microservice.model.Customer;
import cz.cuni.mff.java.kurinna.microservice.repository.CustomerRepository;
import cz.cuni.mff.java.kurinna.microservice.repository.LineItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QueryService {
    private final CustomerRepository customerRepository;
    private final LineItemRepository lineItemRepository;

    public QueryService(CustomerRepository customerRepository, LineItemRepository lineItemRepository) {
        this.customerRepository = customerRepository;
        this.lineItemRepository = lineItemRepository;
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

    public List<PricingSummary> getPricingSummary(int randomNumber) {
        return lineItemRepository.findPricingSummaryReport(randomNumber);
    }
}
