package org.redischool.components;

import org.redischool.models.Course;
import org.redischool.models.User;
import org.redischool.models.UserCourse;
import org.redischool.services.CourseService;
import org.redischool.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * Created by ReDI on 2/3/2017.
 */
@Component
public class Receiver {

    private final CourseService courseService;

    private final UserService userService;

    @Autowired
    public Receiver(CourseService courseService, UserService userService) {
        this.courseService = courseService;
        this.userService = userService;
    }

    @Transactional
    @JmsListener(destination = "activate")
    public void receiveMessage(UserCourse userCourse) {
        User user = userService.findById(userCourse.getUserId());
        System.out.println("Received user courses<" + user.getCourses().size() + ">");

        Set<Course> courseSet = user.getCourses();
        courseSet.add(courseService.findById(userCourse.getCourseId()));

        userService.save(user.toBuilder().courses(courseSet).build());

        System.out.println("Received user id<" + user.getId() + ">");
        System.out.println("Received user courses<" + user.getCourses().size() + ">");
        System.out.println("Received user courses<" + user.getCourses().iterator().next().getName() + ">");

        System.out.println("Received <" + userCourse.getId().toString() + ">");
    }
}
