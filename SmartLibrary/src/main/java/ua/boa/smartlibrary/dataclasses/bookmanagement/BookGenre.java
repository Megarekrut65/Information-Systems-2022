package ua.boa.smartlibrary.dataclasses.bookmanagement;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "book_genre")
public class BookGenre {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_genre_id_generator")
    @SequenceGenerator(name = "book_genre_id_generator", sequenceName = "book_genre_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "genre_id", nullable = false)
    private Genre genre;
    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    public BookGenre(Genre genre, Book book) {
        this.genre = genre;
        this.book = book;
    }
}
