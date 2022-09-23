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
@Table(name = "book_lost")
public class BookLost {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_lost_id_generator")
    @SequenceGenerator(name = "book_lost_id_generator", sequenceName = "book_lost_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "book_count", nullable = false)
    private Integer bookCount;
    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;
    @Column(name = "cause_of_loss", nullable = false)
    private String causeOfLoss;
    @Column(name = "date_of_loss")
    private Date dateOfLoss;
    @Column(name = "was_returned")
    private Boolean wasReturned;
    public BookLost(Integer bookCount, Book book, String causeOfLoss, Date dateOfLoss, Boolean wasReturned) {
        this.bookCount = bookCount;
        this.book = book;
        this.causeOfLoss = causeOfLoss;
        this.dateOfLoss = dateOfLoss;
        this.wasReturned = wasReturned;
    }
}
