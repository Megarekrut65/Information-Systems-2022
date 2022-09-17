package ua.boa.smartlibrary.dataclasses;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "genre_id_generator")
    @SequenceGenerator(name="genre_id_generator", sequenceName = "genre_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name="name", nullable = false)
    private String name;

    public Genre(String name) {
        this.name = name;
    }
}
