package ua.boa.smartlibrary.dataclasses.bookcirculationmanagement;

import lombok.*;
import ua.boa.smartlibrary.dataclasses.bookmanagement.Book;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "book_delivery")
public class BookDelivery {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_delivery_id_generator")
    @SequenceGenerator(name="book_delivery_id_generator", sequenceName = "book_delivery_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;
    @ManyToOne
    @JoinColumn(name="delivery_id", nullable = false)
    private Delivery delivery;
    @ManyToOne
    @JoinColumn(name="book_id", nullable = false)
    private Book book;
    @Column(name="book_count", nullable = false)
    private Integer bookCount;
    @Column(name="book_price", nullable = false)
    private Integer bookPrice;

    public BookDelivery(Delivery delivery, Book book, Integer bookCount, Integer bookPrice) {
        this.delivery = delivery;
        this.book = book;
        this.bookCount = bookCount;
        this.bookPrice = bookPrice;
    }
}
