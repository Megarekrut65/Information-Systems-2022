package ua.boa.smartlibrary.dataclasses;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "publishing_house")
public class PublishingHouse {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "publishing_house_id_generator")
    @SequenceGenerator(name="publishing_house_id_generator", sequenceName = "publishing_house_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name="name", nullable = false)
    private String name;
    @Column(name="address", nullable = false)
    private String address;

    public PublishingHouse(String name, String address) {
        this.name = name;
        this.address = address;
    }
}
