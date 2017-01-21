package org.redischool.services;

import org.redischool.models.User;
import org.redischool.services.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Created by aurel on 16/01/17.
 */
@Service
public class UserService extends AbstractService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository repository) {
        this.userRepository = repository;
    }

    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public List<User> save(List<User> users) {
        return userRepository.save(users);
    }

    @edu.umd.cs.findbugs.annotations.SuppressFBWarnings("RV_RETURN_VALUE_IGNORED_NO_SIDE_EFFECT")
    @Transactional
    public User findById(UUID id) {
        User user = userRepository.findOne(id);
        user.getRoles().size();
        user.getContacts().size();
        user.getCourses().size();
        return user;
    }

    @Transactional
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }


}
