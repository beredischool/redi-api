package org.redischool.services;

import org.redischool.models.User;
import org.redischool.services.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by aurel on 16/01/17.
 */
@Service
public class UserService extends AbstractService {

    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public User save(User user) {
        return repository.save(user);
    }
}
