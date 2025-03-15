package cz.cuni.mff.java.kurinna.microservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;

import java.sql.Timestamp;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @Email
    private String email;

    private String password;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Timestamp crated_at;

    public User() {}

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
