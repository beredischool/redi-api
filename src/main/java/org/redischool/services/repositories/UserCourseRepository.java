package org.redischool.services.repositories;

import org.redischool.models.UserCourse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Created by aurel on 03/02/17.
 */
public interface UserCourseRepository extends JpaRepository<UserCourse, UUID> {
}
