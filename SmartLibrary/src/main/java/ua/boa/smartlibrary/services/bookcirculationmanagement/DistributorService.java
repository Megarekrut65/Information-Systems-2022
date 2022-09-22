package ua.boa.smartlibrary.services.bookcirculationmanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.boa.smartlibrary.dataclasses.bookcirculationmanagement.Distributor;
import ua.boa.smartlibrary.db.repositories.bookcirculationmanagement.DistributorRepository;
import ua.boa.smartlibrary.exceptions.bookcirculationmanagement.DistributorNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class DistributorService {
    @Autowired
    private DistributorRepository repository;

    public Distributor create(String name, String phoneNumber, String address){
        return repository.save(new Distributor(name, phoneNumber, address));
    }
    public List<Distributor> getAll(){
        return repository.findAll();
    }
    public Distributor update(Integer id, String name, String phoneNumber, String address){
        Distributor distributor = get(id);
        distributor.setName(name);
        distributor.setPhoneNumber(phoneNumber);
        distributor.setAddress(address);
        return repository.save(distributor);
    }
    public void remove(Integer id){
        Distributor distributor = get(id);
        repository.delete(distributor);
    }
    public List<Distributor> findByName(String name){
        return repository.findDistributorsByNameAdvanced(name);
    }
    public Distributor get(Integer id){
        Optional<Distributor> optional = repository.findById(id);
        if(optional.isEmpty()) throw new DistributorNotFoundException(id);
        return optional.get();
    }
}
