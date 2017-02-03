package org.redischool.services.repositories;

import org.redischool.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Created by raouf on 1/20/17.
 */
public interface RoleRepository extends JpaRepository<Role, UUID> {
    public Role findByName(String name);
}
