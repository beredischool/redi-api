package org.redischool.services.repositories;

import org.redischool.models.User;
import org.redischool.models.UserType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/**
 * Created by aurel on 16/01/17.
 */
public interface UserRepository extends JpaRepository<User, UUID> {

    User findByEmail(String email);

    List<User> findByUserType(UserType userType);

}
