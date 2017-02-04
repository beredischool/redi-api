package org.redischool.services.repositories;

import org.redischool.models.Contact;
import org.redischool.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/**
 * Created by aurelavramescu on 04/02/17.
 */
public interface ContactRepository extends JpaRepository<Contact, UUID> {

    List<Contact> findByUser(User user);
}
