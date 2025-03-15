package cz.cuni.mff.java.kurinna.microservice.service;

import cz.cuni.mff.java.kurinna.microservice.model.User;
import cz.cuni.mff.java.kurinna.microservice.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QueryService {
    private final UserRepository userRepository;

    public QueryService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getResults() {
        return userRepository.findAll();
    }

    public void deleteAll() {
        userRepository.deleteAll();
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
}
