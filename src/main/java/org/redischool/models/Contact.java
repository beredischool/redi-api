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
 * Created by ReDI on 1/14/2017.
 */
@Entity
@Table(name = "CONTACT", uniqueConstraints = {@UniqueConstraint(columnNames = {"CONTACT_ID"})})
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = {"id"})
@Builder(toBuilder = true)
@Getter
public class Contact {
    @Id
    @Column(name = "CONTACT_ID")
    private String id;

    @Column(name = "NAME")
    private String title;

    @Column(name = "VALUE")
    private String value;

    @Column(name = "CONTACT_TYPE")
    private ContactType contactType;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

}
