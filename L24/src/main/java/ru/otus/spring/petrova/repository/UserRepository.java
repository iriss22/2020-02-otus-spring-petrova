package ru.otus.spring.petrova.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.petrova.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

  User findByUsername(String username);
}
