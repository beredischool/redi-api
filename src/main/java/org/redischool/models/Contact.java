package org.redischool.models;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.UUID;

/**
 * Created by ReDI on 1/14/2017.
 */
@Entity
@Table(name = "CONTACT", uniqueConstraints = {@UniqueConstraint(
        columnNames = {"USER_ID", "CONTACT_TYPE", "VALUE"})})
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = {"id"})
@Builder(toBuilder = true)
@Getter
public class Contact {
    @Id
    @Column(name = "ID")
    private UUID id;

    @Column(name = "NAME")
    private String title;

    @Column(name = "VALUE")
    private String value;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "CONTACT_TYPE")
    private ContactType contactType;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

}
