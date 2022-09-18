package ua.boa.smartlibrary.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.boa.smartlibrary.dataclasses.Genre;
import ua.boa.smartlibrary.db.repositories.GenreRepository;
import ua.boa.smartlibrary.exceptions.GenreNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class GenreService {
    @Autowired
    private GenreRepository repository;

    public Genre create(String name){
        return repository.save(new Genre(name));
    }
    public List<Genre> getAll(){
        return repository.findAll();
    }
    public Genre update(Integer id, String name){
        Genre genre = get(id);
        genre.setName(name);
        return repository.save(genre);
    }
    public void remove(Integer id){
        Genre genre = get(id);
        repository.delete(genre);
    }
    public Genre get(Integer id){
        Optional<Genre> optional = repository.findById(id);
        if(optional.isEmpty()) throw new GenreNotFoundException(id);
        return optional.get();
    }
}
