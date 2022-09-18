package ua.boa.smartlibrary.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.boa.smartlibrary.dataclasses.Book;
import ua.boa.smartlibrary.dataclasses.PublishingHouse;
import ua.boa.smartlibrary.db.repositories.BookRepository;
import ua.boa.smartlibrary.exceptions.BookNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    @Autowired
    private BookRepository repository;
    @Autowired
    private PublishingHouseService publishingHouseService;

    public Book create(String title, Integer publishingHouseId, Integer publishYear, String comment){
        PublishingHouse publishingHouse = publishingHouseService.get(publishingHouseId);
        return repository.save(new Book(title, publishingHouse, publishYear, comment));
    }
    public List<Book> getAll(){
        return repository.findAll();
    }
    public Book update(Integer id, String title, Integer publishingHouseId, Integer publishYear, String comment){
        PublishingHouse publishingHouse = publishingHouseService.get(publishingHouseId);
        Book book = get(id);
        book.setTitle(title);
        book.setPublishingHouse(publishingHouse);
        book.setPublishYear(publishYear);
        book.setComment(comment);
        return repository.save(book);
    }
    public void remove(Integer id){
        Book book = get(id);
        repository.delete(book);
    }
    public List<Book> findByTitle(String title){
        return repository.findBookByTitleAdvanced(title);
    }
    public List<Book> findByPublishingHouse(Integer publishingHouseId){
        PublishingHouse publishingHouse = publishingHouseService.get(publishingHouseId);
        return repository.findBooksByPublishingHouse(publishingHouse);
    }
    public Book get(Integer id){
        Optional<Book> optional = repository.findById(id);
        if(optional.isEmpty()) throw new BookNotFoundException(id);
        return optional.get();
    }
}
