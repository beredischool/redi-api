package org.redischool.models;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.Set;

/**
 * Created by ReDI on 1/16/2017.
 */

@Entity
@Table(name = "LOCATION", uniqueConstraints = {@UniqueConstraint(columnNames = {"LOCATION_ID"})})
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = {"id"})
@Builder(toBuilder = true)
@Getter

public class Location {
    @Id
    @Column(name = "LOCATION_ID")
    private String id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "ADDRESS")
    private String address;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "location")
    private Set<Session> sessions;

}
