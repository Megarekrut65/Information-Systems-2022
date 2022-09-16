package ua.boa.smartlibrary.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.boa.smartlibrary.dataclasses.Genre;
import ua.boa.smartlibrary.db.repositories.GenreRepository;

import java.util.List;
import java.util.Map;

@RestController
public class GenreController {
    @Autowired
    private GenreRepository repository;

    @RequestMapping(value = "/genre", method = RequestMethod.POST)
    public Genre create(@RequestParam("name")String name){
        return repository.save(new Genre(name));
    }
    @GetMapping("/genre")
    public List<Genre> getAll(){
        return repository.findAll();
    }
}
