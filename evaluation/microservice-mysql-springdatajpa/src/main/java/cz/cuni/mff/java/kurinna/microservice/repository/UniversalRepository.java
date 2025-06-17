package cz.cuni.mff.java.kurinna.microservice.repository;

import org.springframework.data.repository.NoRepositoryBean;

import java.time.LocalDate;
import java.util.List;

@NoRepositoryBean
public interface UniversalRepository {
    // Basic queries
    List<Object[]> a1();
    List<Object[]> a2(LocalDate startDate, LocalDate endDate);
    List<Object[]> a3();
    List<Object[]> a4(int minOrderKey, int maxOrderKey);
    List<Object[]> b1();
    List<Object[]> b2();
    List<Object[]> c1();
    List<Object[]> c2();
    List<Object[]> c3();
    List<Object[]> c4();
    List<Object[]> c5();
    List<Object[]> d1();
    List<Object[]> d2();
    List<Object[]> d3();
    List<Object[]> e1();
    List<Object[]> e2();
    List<Object[]> e3();

    // Advanced queries
    List<Object[]> q1(int days);
    List<Object[]> q2(int size, String type, String region);
    List<Object[]> q3(String segment, LocalDate orderDate, LocalDate shipDate);
    List<Object[]> q4(LocalDate orderDate);
    List<Object[]> q5(String region, LocalDate orderDate);
}