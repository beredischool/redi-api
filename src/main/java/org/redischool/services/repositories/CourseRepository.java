package org.redischool.services.repositories;

import org.redischool.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Created by ReDI on 1/27/2017.
 */
public interface CourseRepository extends JpaRepository<Course, UUID> {

    Course findByName(String name);

    Course findByUrl(String url);

}
