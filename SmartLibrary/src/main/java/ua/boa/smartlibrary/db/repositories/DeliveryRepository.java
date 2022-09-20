package ua.boa.smartlibrary.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.boa.smartlibrary.dataclasses.Delivery;
import ua.boa.smartlibrary.dataclasses.Distributor;

import java.sql.Date;
import java.util.List;

public interface DeliveryRepository extends JpaRepository<Delivery, Integer> {
    List<Delivery> findDeliveriesByDateOfDeliveryBetween(Date dateOfDeliveryMin, Date dateOfDeliveryMax);
    List<Delivery> findDeliveriesByDistributor(Distributor distributor);
}
