package ua.boa.smartlibrary.dataclasses.bookmanagement;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Authorship {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "authorship_id_generator")
    @SequenceGenerator(name = "authorship_id_generator", sequenceName = "authorship_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "author_role", nullable = false)
    private String authorRole;
    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;
    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    public Authorship(String authorRole, Book book, Author author) {
        this.authorRole = authorRole;
        this.book = book;
        this.author = author;
    }
}
