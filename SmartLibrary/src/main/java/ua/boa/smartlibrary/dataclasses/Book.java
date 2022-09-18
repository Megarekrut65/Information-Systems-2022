package ua.boa.smartlibrary.dataclasses;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_id_generator")
    @SequenceGenerator(name="book_id_generator", sequenceName = "book_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name="title", nullable = false)
    private String title;
    @ManyToOne
    @JoinColumn(name="publishing_house_id")
    private PublishingHouse publishingHouse;
    @Column(name="publish_year", nullable = false)
    private Integer publishYear;
    @Column(name = "comment", nullable = false)
    private String comment;

    public Book(String title, PublishingHouse publishingHouse, Integer publishYear, String comment) {
        this.title = title;
        this.publishingHouse = publishingHouse;
        this.publishYear = publishYear;
        this.comment = comment;
    }
}
