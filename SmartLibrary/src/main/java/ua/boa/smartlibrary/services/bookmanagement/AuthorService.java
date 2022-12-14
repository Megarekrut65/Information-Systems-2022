package ua.boa.smartlibrary.services.bookmanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.boa.smartlibrary.dataclasses.bookmanagement.Author;
import ua.boa.smartlibrary.db.repositories.bookmanagement.AuthorRepository;
import ua.boa.smartlibrary.exceptions.bookmanagement.AuthorNotFoundException;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepository repository;

    public Author create(String name, Date dateOfBirth, Date dateOfDeath) {
        return repository.save(new Author(name, dateOfBirth, dateOfDeath));
    }

    public List<Author> getAll() {
        return repository.findAll();
    }

    public Author update(Integer id, String name, Date dateOfBirth, Date dateOfDeath) {
        Author author = get(id);
        author.setName(name);
        author.setDateOfBirth(dateOfBirth);
        author.setDateOfDeath(dateOfDeath);
        return repository.save(author);
    }

    public void remove(Integer id) {
        Author author = get(id);
        repository.delete(author);
    }

    public List<Author> findByName(String name) {
        return repository.findAuthorsByNameAdvanced(name);
    }

    public Author get(Integer id) {
        Optional<Author> optional = repository.findById(id);
        if (optional.isEmpty()) throw new AuthorNotFoundException(id);
        return optional.get();
    }
}
