package ua.boa.smartlibrary.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.boa.smartlibrary.dataclasses.PublishingHouse;
import ua.boa.smartlibrary.db.repositories.PublishingHouseRepository;
import ua.boa.smartlibrary.exceptions.PublishingHouseNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class PublishingHouseService {
    @Autowired
    private PublishingHouseRepository repository;
    public PublishingHouse create(String name, String address){
        return repository.save(new PublishingHouse(name, address));
    }
    public List<PublishingHouse> getAll(){
        return repository.findAll();
    }
    public PublishingHouse update(Integer id, String name, String address){
        PublishingHouse publishingHouse = get(id);
        publishingHouse.setName(name);
        publishingHouse.setAddress(address);
        return repository.save(publishingHouse);
    }
    public void remove(Integer id){
        PublishingHouse publishingHouse = get(id);
        repository.delete(publishingHouse);
    }
    public List<PublishingHouse> findByName(String name){
        return repository.findPublishingHouseByNameAdvanced(name);
    }
    public PublishingHouse get(Integer id){
        Optional<PublishingHouse> optional = repository.findById(id);
        if(optional.isEmpty()) throw new PublishingHouseNotFoundException(id);
        return optional.get();
    }
}
