package org.redischool.services;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.redischool.models.User;
import org.redischool.models.UserType;
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
@SuppressFBWarnings("RV_RETURN_VALUE_IGNORED_NO_SIDE_EFFECT")
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

    @Transactional
    public User findById(UUID id) {
        User user = userRepository.findOne(id);
//        user.getRoles().size();
        user.getContacts().size();
        user.getCourses().size();
        return user;
    }

    @Transactional
    public User findByEmail(String email) {
        User user = userRepository.findByEmail(email);
        //      user.getRoles().size();
        if (user == null) {
            return null;
        }
        user.getContacts().size();
        user.getCourses().size();
        return user;
    }

    @Transactional
    public List<User> findByUserType(UserType userType) {
        List<User> users = userRepository.findByUserType(userType);
        for (int i = 0; i < users.size(); i++) {
            //      user.getRoles().size();
            users.get(i).getContacts().size();
            users.get(i).getCourses().size();
        }
        return users;
    }

    @Transactional
    public Page<User> findAll(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);

        for (int i = 0; i < users.getContent().size(); i++) {
            users.getContent().get(i).getContacts().size();
            users.getContent().get(i).getCourses().size();
        }
        return users;
    }

    @Transactional
    public User login(String email, String password) {
        User user = userRepository.findByEmailAndPassword(email, password);
        if (user == null) {
            return null;
        }
        user.getContacts().size();
        user.getCourses().size();
        return user;

    }


    @Transactional
    public User signUp(UUID id, String email, String password,
                       String firstName, String lastName, String address,
                       String description, UserType userType) {

        User user = userRepository.save(User.builder().id(id).email(email).password(password).
                firstName(firstName).lastName(lastName).address(address).
                description(description).userType(userType).build());

//        user.getContacts().size();
        //      user.getCourses().size();

        return user;
    }
}
