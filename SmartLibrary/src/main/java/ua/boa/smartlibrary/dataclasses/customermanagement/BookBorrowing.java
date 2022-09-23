package ua.boa.smartlibrary.dataclasses.customermanagement;

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
@Table(name = "book_borrowing")
public class BookBorrowing {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_borrowing_id_generator")
    @SequenceGenerator(name = "book_borrowing_id_generator", sequenceName = "book_borrowing_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "date_of_borrowing", nullable = false)
    private Date dateOfBorrowing;
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;
    @Column(name = "estimated_date_of_return", nullable = false)
    private Date estimatedDateOfReturn;
    @Column(name = "actual_date_of_return")
    private Date actualDateOfReturn;
    @Column(name = "comment", nullable = false)
    private String comment;

    public BookBorrowing(Date dateOfBorrowing, Customer customer,
                         Book book, Date estimatedDateOfReturn, Date actualDateOfReturn, String comment) {
        this.dateOfBorrowing = dateOfBorrowing;
        this.customer = customer;
        this.book = book;
        this.estimatedDateOfReturn = estimatedDateOfReturn;
        this.actualDateOfReturn = actualDateOfReturn;
        this.comment = comment;
    }
}
