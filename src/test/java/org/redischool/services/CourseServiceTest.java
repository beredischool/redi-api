package org.redischool.services;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redischool.models.Course;
import org.redischool.services.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ReDI on 2/3/2017.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class CourseServiceTest {

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseRepository courseRepository;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
        courseRepository.deleteAll();
    }


    public Course createCourse(String name) {

        Course course = Course.builder().id(courseService.generateId()).name(name).description("New Technologies")
                .url("www.redi-school.org/Courses/").build();
        return course;
    }

    @Test
    public void shouldSaveCourseSuccessful() throws Exception {

        Course course = createCourse("Java");
        Course savedCourse = courseService.save(course);
        Assert.assertThat(savedCourse, Matchers.equalTo(course));

    }

    @Test
    public void shouldSaveCourseListSuccessful() throws Exception {

        List<Course> courseList = new ArrayList<>();
        courseList.add(createCourse("Java7"));
        courseList.add(createCourse("Android"));
        courseList.add(createCourse("Digital Sales"));
        courseList.add(createCourse("Project Management"));

        List<Course> savedCourseList = courseService.save(courseList);
        Assert.assertThat(savedCourseList, Matchers.equalTo(courseList));

    }


    @Test
    public void shouldFindByIdSuccessful() throws Exception {
        Course course = createCourse("java8");
        courseService.save(course);
        Course savedCourse = courseService.findById(course.getId());
        Assert.assertThat(savedCourse, Matchers.equalTo(course));

    }

    @Test
    public void shouldFindByNameSuccessful() throws Exception {
        Course course = createCourse("Java Backend");
        courseService.save(course);
        Course savedCourse = courseService.findByName(course.getName());
        Assert.assertThat(savedCourse, Matchers.equalTo(course));
    }

    @Test
    public void shouldFindByUrlSuccessful() throws Exception {
        Course course = createCourse("Java Frontend");
        courseService.save(course);
        Course savedCourse = courseService.findByUrl(course.getUrl());
        Assert.assertThat(savedCourse, Matchers.equalTo(course));
    }

    @Test
    public void shouldFindByAllProPageSuccessful() throws Exception {
        List<Course> courseList = new ArrayList<>();
        courseList.add(createCourse("Java6"));
        courseList.add(createCourse("Android5"));
        courseList.add(createCourse("Sales"));
        courseList.add(createCourse("Management"));

        courseService.save(courseList);

        Page<Course> coursePage = courseService.findAll(new PageRequest(0, 5));

        Assert.assertThat(coursePage,
                Matchers.allOf(
                        Matchers.hasProperty("totalPages", Matchers.equalTo(1)),
                        Matchers.hasProperty("totalElements", Matchers.equalTo(4L))));

    }

}