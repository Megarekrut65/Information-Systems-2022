package ua.boa.smartlibrary.services.bookcirculationmanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.boa.smartlibrary.PageGetter;
import ua.boa.smartlibrary.dataclasses.bookcirculationmanagement.Delivery;
import ua.boa.smartlibrary.dataclasses.bookcirculationmanagement.Distributor;
import ua.boa.smartlibrary.dataclasses.customermanagement.BookBorrowing;
import ua.boa.smartlibrary.db.repositories.bookcirculationmanagement.DeliveryRepository;

import java.sql.Date;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class DeliverySearchService extends PageGetter<Delivery> {
    @Autowired
    private DeliveryRepository deliveryRepository;
    @Autowired
    private DistributorService distributorService;

    public List<Delivery> findDeliveriesByAll(String distributorName, Date minDate, Date maxDate) {
        List<Distributor> distributors = distributorName.equals("") ? distributorService.getAll() :
                distributorService.findByName(distributorName);
        if (minDate != null && maxDate != null)
            return deliveryRepository
                    .findDeliveriesByDateOfDeliveryBetweenAndDistributorIn(minDate, maxDate, distributors);
        if (minDate != null)
            return deliveryRepository.findDeliveriesByDateOfDeliveryGreaterThanAndDistributorIn(minDate, distributors);
        if (maxDate != null)
            return deliveryRepository.findDeliveriesByDateOfDeliveryLessThanAndDistributorIn(maxDate, distributors);
        return deliveryRepository.findDeliveriesByDistributorIn(distributors);
    }
    public List<Delivery> getPage(int page, int perPage, List<Delivery> deliveries){
        return getPart(deliveries, page, perPage,(Delivery delivery1, Delivery delivery2)->
                        delivery2.getDateOfDelivery().compareTo(delivery1.getDateOfDelivery()));
    }
}
