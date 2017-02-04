package org.redischool.services;

import org.redischool.models.Contact;
import org.redischool.models.User;
import org.redischool.services.repositories.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by aurelavramescu on 04/02/17.
 */
public class ContactService extends AbstractService {

    private final ContactRepository repository;

    @Autowired
    public ContactService(ContactRepository repository) {
        this.repository = repository;
    }


    @Transactional
    public Contact save(Contact contact) {
        return repository.save(contact);
    }

    @Transactional(readOnly = true)
    public List<Contact> getAll() {
        return repository.findAll();
    }

    @Transactional
    public List<Contact> findByUser(User user) {
        return repository.findByUser(user);
    }
}


