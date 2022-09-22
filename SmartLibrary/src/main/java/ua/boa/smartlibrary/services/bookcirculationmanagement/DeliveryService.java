package ua.boa.smartlibrary.services.bookcirculationmanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.boa.smartlibrary.dataclasses.bookcirculationmanagement.Delivery;
import ua.boa.smartlibrary.dataclasses.bookcirculationmanagement.Distributor;
import ua.boa.smartlibrary.db.repositories.bookcirculationmanagement.DeliveryRepository;
import ua.boa.smartlibrary.exceptions.bookcirculationmanagement.DeliveryNotFoundException;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class DeliveryService {
    @Autowired
    private DeliveryRepository repository;
    @Autowired
    private DistributorService distributorService;

    public Delivery create(Date dateOfDelivery, Integer distributorId){
        Distributor distributor = distributorService.get(distributorId);
        return repository.save(new Delivery(dateOfDelivery, distributor));
    }
    public List<Delivery> getAll(){
        return repository.findAll();
    }
    public Delivery update(Integer id, Date dateOfDelivery, Integer distributorId){
        Distributor distributor = distributorService.get(distributorId);
        Delivery delivery = get(id);
        delivery.setDateOfDelivery(dateOfDelivery);
        delivery.setDistributor(distributor);
        return repository.save(delivery);
    }
    public void remove(Integer id){
        Delivery delivery = get(id);
        repository.delete(delivery);
    }
    public List<Delivery> findByDistributor(Integer distributorId){
        Distributor distributor = distributorService.get(distributorId);
        return repository.findDeliveriesByDistributor(distributor);
    }
    public List<Delivery> findByDatePeriod(Date dateOfDeliveryMin, Date dateOfDeliveryMax){
        return repository.findDeliveriesByDateOfDeliveryBetween(dateOfDeliveryMin,dateOfDeliveryMax);
    }
    public Delivery get(Integer id){
        Optional<Delivery> optional = repository.findById(id);
        if(optional.isEmpty()) throw new DeliveryNotFoundException(id);
        return optional.get();
    }
}