package org.redischool.services.repositories;

import org.redischool.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Created by aurel on 16/01/17.
 */
public interface UserRepository extends JpaRepository<User, UUID> {

    User findByEmail(String email);
}
