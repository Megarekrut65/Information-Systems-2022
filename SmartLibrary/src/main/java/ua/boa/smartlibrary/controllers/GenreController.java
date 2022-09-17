package ua.boa.smartlibrary.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.boa.smartlibrary.dataclasses.Genre;
import ua.boa.smartlibrary.db.repositories.GenreRepository;
import ua.boa.smartlibrary.services.GenreService;

import java.util.List;

@RestController
public class GenreController {
    @Autowired
    private GenreService service;

    @RequestMapping(value = "/genre", method = RequestMethod.POST)
    public Genre create(@RequestParam("name")String name){
        return service.create(name);
    }
    @RequestMapping(value = "/genre", method = RequestMethod.PUT)
    public Genre update(@RequestParam("id")String id, @RequestParam("name")String name){
        return service.update(Integer.parseInt(id), name);
    }
    @GetMapping("/genre")
    public List<Genre> getAll(){
        return service.getAll();
    }
}
