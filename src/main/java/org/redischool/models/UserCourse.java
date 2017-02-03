package org.redischool.models;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

/**
 * Created by ReDI on 1/29/2017.
 */
@Entity
@Table(name = "USER_COURSES")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = {"id"})
@Builder(toBuilder = true)
@Getter
public class UserCourse {

    @Id
    @Column(name = "ID")
    private UUID id;

    @Column(name = "COURSE_ID")
    private UUID courseId;

    @Column(name = "USER_ID")
    private UUID userId;

    @Column(name = "STATUS")
    private CourseStatus courseStatus;
}
