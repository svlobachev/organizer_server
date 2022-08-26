package ru.svlobachev.organizer_server.registration.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.svlobachev.organizer_server.registration.model.entity.Users;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

    List<Users> findByValue3(String phoneNumber);
}
