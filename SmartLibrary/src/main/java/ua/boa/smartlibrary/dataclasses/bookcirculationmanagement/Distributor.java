package ua.boa.smartlibrary.dataclasses.bookcirculationmanagement;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Distributor {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "distributor_id_generator")
    @SequenceGenerator(name = "distributor_id_generator", sequenceName = "distributor_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;
    @Column(name = "address")
    private String address;

    public Distributor(String name, String phoneNumber, String address) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }
}
