package com.vv.VisualVoyage.repositories;

import com.vv.VisualVoyage.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.firstName ILIKE %:query% OR u.lastName ILIKE %:query% OR u.email ILIKE %:query%")
    List<User> searchUser(@Param("query") String query);
}
