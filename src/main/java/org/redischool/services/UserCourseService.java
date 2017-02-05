package org.redischool.services;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.redischool.models.UserCourse;
import org.redischool.services.repositories.UserCourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Created by ReDI on 2/4/2017.
 */
@SuppressFBWarnings("RV_RETURN_VALUE_IGNORED_NO_SIDE_EFFECT")
@Service
public class UserCourseService extends AbstractService {

    private final UserCourseRepository userCourseRepository;

    @Autowired
    public UserCourseService(UserCourseRepository userCourseRepository) {

        this.userCourseRepository = userCourseRepository;
    }


    @Transactional
    public UserCourse save(UserCourse userCourse) {
        return userCourseRepository.save(userCourse);
    }

    @Transactional
    public List<UserCourse> save(List<UserCourse> userCourseList) {
        return userCourseRepository.save(userCourseList);
    }

    @Transactional
    public UserCourse findById(UUID id) {
        UserCourse userCourse = userCourseRepository.findOne(id);
        return userCourse;
    }

}
