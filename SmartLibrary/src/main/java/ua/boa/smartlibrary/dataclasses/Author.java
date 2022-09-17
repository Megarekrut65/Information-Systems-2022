package ua.boa.smartlibrary.dataclasses;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "author_id_generator")
    @SequenceGenerator(name="author_id_generator", sequenceName = "author_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name="name", nullable = false)
    private String name;
    @Column(name = "date_of_birth")
    private Date dateOfBirth;
    @Column(name = "date_of_death")
    private Date dateOfDeath;

    public Author(String name) {
        this.name = name;
        dateOfBirth = null;
        dateOfDeath = null;
    }

    public Author(String name, Date dateOfBirth, Date dateOfDeath) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.dateOfDeath = dateOfDeath;
    }
}
