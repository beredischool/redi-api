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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Created by ReDI on 1/15/2017.
 */

@Entity
@Table(name = "SESSION", uniqueConstraints = {@UniqueConstraint(columnNames = {"SESSION_ID"})})
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = {"id"})
@Builder(toBuilder = true)
@Getter
public class Session {
    @Id
    @Column(name = "SESSION_ID")
    private String id;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "START_HOUR")
    private byte time;


    @Column(name = "DURATION")
    private byte duration;

    @Column(name = "DATE")
    private String date;

    @ManyToOne
    @JoinColumn(name = "COURSE_ID")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "LOCATION_ID")
    private Location location;
}
