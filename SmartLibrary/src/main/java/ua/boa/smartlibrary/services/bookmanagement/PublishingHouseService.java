package ua.boa.smartlibrary.services.bookmanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.boa.smartlibrary.PageGetter;
import ua.boa.smartlibrary.dataclasses.bookmanagement.PublishingHouse;
import ua.boa.smartlibrary.dataclasses.bookmanagement.Tag;
import ua.boa.smartlibrary.db.repositories.bookmanagement.PublishingHouseRepository;
import ua.boa.smartlibrary.exceptions.bookmanagement.PublishingHouseNotFoundException;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class PublishingHouseService extends PageGetter<PublishingHouse> {
    @Autowired
    private PublishingHouseRepository repository;

    public PublishingHouse create(String name, String address) {
        return repository.save(new PublishingHouse(name, address));
    }

    public List<PublishingHouse> getAll() {
        return repository.getAllOrdered();
    }

    public PublishingHouse update(Integer id, String name, String address) {
        PublishingHouse publishingHouse = get(id);
        publishingHouse.setName(name);
        publishingHouse.setAddress(address);
        return repository.save(publishingHouse);
    }

    public void remove(Integer id) {
        PublishingHouse publishingHouse = get(id);
        repository.delete(publishingHouse);
    }

    public List<PublishingHouse> findByName(String name) {
        return repository.findPublishingHousesByNameAdvanced(name);
    }
    public List<PublishingHouse> getPage(int page, int perPage, List<PublishingHouse> publishingHouses) {
        return getPart(publishingHouses, page, perPage, Comparator.comparing(PublishingHouse::getName));
    }
    public PublishingHouse get(Integer id) {
        Optional<PublishingHouse> optional = repository.findById(id);
        if (optional.isEmpty()) throw new PublishingHouseNotFoundException(id);
        return optional.get();
    }
}
