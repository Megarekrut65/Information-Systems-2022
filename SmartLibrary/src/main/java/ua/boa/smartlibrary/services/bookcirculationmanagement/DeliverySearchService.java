package ua.boa.smartlibrary.services.bookcirculationmanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.boa.smartlibrary.dataclasses.bookcirculationmanagement.Delivery;
import ua.boa.smartlibrary.dataclasses.bookcirculationmanagement.Distributor;
import ua.boa.smartlibrary.db.repositories.bookcirculationmanagement.DeliveryRepository;

import java.sql.Date;
import java.util.List;

@Service
public class DeliverySearchService {
    @Autowired
    private DeliveryRepository deliveryRepository;
    @Autowired
    private DistributorService distributorService;
    public List<Delivery> findDeliveriesByAll(String distributorName, Date minDate, Date maxDate){
        List<Distributor> distributors = distributorName.equals("")?distributorService.getAll():
                distributorService.findByName(distributorName);
        if(minDate != null && maxDate != null)
            return deliveryRepository
                    .findDeliveriesByDateOfDeliveryBetweenAndDistributorIn(minDate, maxDate, distributors);
        if(minDate != null)
            return deliveryRepository.findDeliveriesByDateOfDeliveryGreaterThanAndDistributorIn(minDate, distributors);
        if(maxDate != null)
            return deliveryRepository.findDeliveriesByDateOfDeliveryLessThanAndDistributorIn(maxDate, distributors);
        return deliveryRepository.findDeliveriesByDistributorIn(distributors);
    }
}
