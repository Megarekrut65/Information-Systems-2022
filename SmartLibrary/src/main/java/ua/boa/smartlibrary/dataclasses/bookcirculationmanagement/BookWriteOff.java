package ua.boa.smartlibrary.dataclasses.bookcirculationmanagement;

import lombok.*;
import ua.boa.smartlibrary.dataclasses.bookmanagement.Book;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "book_write-off")
public class BookWriteOff {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_write-off_id_generator")
    @SequenceGenerator(name="book_write-off_id_generator", sequenceName = "book_write-off_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "date_of_write-off")
    private Date dateOfWriteOff;
    @ManyToOne
    @JoinColumn(name="book_id", nullable = false)
    private Book book;
    @Column(name="book_count", nullable = false)
    private Integer bookCount;

    public BookWriteOff(Date dateOfWriteOff, Book book, Integer bookCount) {
        this.dateOfWriteOff = dateOfWriteOff;
        this.book = book;
        this.bookCount = bookCount;
    }
}
