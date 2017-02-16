package org.redischool.services;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.redischool.models.Course;
import org.redischool.services.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Created by ReDI on 1/27/2017.
 */
@SuppressFBWarnings("RV_RETURN_VALUE_IGNORED_NO_SIDE_EFFECT")
@Service
public class CourseService extends AbstractService {

    private final CourseRepository courseRepository;


    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Transactional
    public Course save(Course course) {
        return courseRepository.save(course);
    }

    @Transactional
    public List<Course> save(List<Course> courses) {
        return courseRepository.save(courses);
    }


    @Transactional
    public Course findById(UUID id) {
        Course course = courseRepository.findOne(id);
        if (course == null) {
            return null;
        }
        course.getUsers().size();
        course.getSessions().size();
        return course;
    }


    @Transactional
    public Course findByName(String name) {
        Course course = courseRepository.findByName(name);
        if (course == null) {
            return null;
        }
        course.getUsers().size();
        course.getSessions().size();
        return course;

    }


    @Transactional
    public Course findByUrl(String url) {
        Course course = courseRepository.findByUrl(url);
        if (course == null) {
            return null;
        }
        course.getUsers().size();
        course.getSessions().size();
        return course;
    }


    @Transactional
    public Page<Course> findAll(Pageable pageable) {
        Page<Course> courses = courseRepository.findAll(pageable);

        for (int i = 0; i < courses.getContent().size(); i++) {
            courses.getContent().get(i).getSessions().size();
            courses.getContent().get(i).getUsers().size();
        }
        return courses;
    }

}
