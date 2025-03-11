package cz.cuni.mff.java.kurinna.microservice.service;

import cz.cuni.mff.java.kurinna.microservice.model.User;
import cz.cuni.mff.java.kurinna.microservice.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QueryService {
    private final UserRepository userRepository;

    public QueryService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void runQuery() {
        if (userRepository.count() == 0) {
            userRepository.save(new User("username", "user@email.xom", "1111"));
        }
    }

    public List<User> getResults() {
        return userRepository.findAll();
    }
}
