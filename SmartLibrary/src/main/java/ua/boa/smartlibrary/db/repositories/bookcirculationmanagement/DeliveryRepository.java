package ua.boa.smartlibrary.db.repositories.bookcirculationmanagement;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.boa.smartlibrary.dataclasses.bookcirculationmanagement.Delivery;
import ua.boa.smartlibrary.dataclasses.bookcirculationmanagement.Distributor;

import java.sql.Date;
import java.util.Collection;
import java.util.List;

public interface DeliveryRepository extends JpaRepository<Delivery, Integer> {
    List<Delivery> findDeliveriesByDateOfDeliveryBetween(Date dateOfDeliveryMin, Date dateOfDeliveryMax);

    List<Delivery> findDeliveriesByDistributor(Distributor distributor);

    List<Delivery> findDeliveriesByDateOfDeliveryBetweenAndDistributorIn(Date dateOfDeliveryMin,
                                                                         Date dateOfDeliveryMax,
                                                                         Collection<Distributor> distributor);

    List<Delivery> findDeliveriesByDateOfDeliveryGreaterThanAndDistributorIn(Date date,
                                                                             Collection<Distributor> distributor);

    List<Delivery> findDeliveriesByDateOfDeliveryLessThanAndDistributorIn(Date date,
                                                                          Collection<Distributor> distributor);

    List<Delivery> findDeliveriesByDistributorIn(Collection<Distributor> distributor);
}
