package ua.boa.smartlibrary.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.boa.smartlibrary.Utilities;
import ua.boa.smartlibrary.dataclasses.Author;
import ua.boa.smartlibrary.services.AuthorService;

import java.sql.Date;
import java.util.List;

@RestController
public class AuthorController {
    @Autowired
    private AuthorService service;

    @RequestMapping(value = "/author", method = RequestMethod.POST)
    public Author create(@RequestParam("name")String name, @RequestParam("dateOfBirth") String dateOfBirth,
                         @RequestParam("dateOfDeath") String dateOfDeath){
        return service.create(name, Utilities.getDate(dateOfBirth), Utilities.getDate(dateOfDeath));
    }
    @RequestMapping(value = "/author", method = RequestMethod.PUT)
    public Author update(@RequestParam("id")String id, @RequestParam("name")String name,
                         @RequestParam("dateOfBirth") String dateOfBirth, @RequestParam("dateOfDeath") String dateOfDeath){
        return service.update(Integer.parseInt(id), name, Utilities.getDate(dateOfBirth), Utilities.getDate(dateOfDeath));
    }
    @RequestMapping(value = "/authors", method = RequestMethod.GET)
    public List<Author> getAll(){
        return service.getAll();
    }
    @RequestMapping(value = "/author", method = RequestMethod.DELETE)
    public void remove(@RequestParam("id")String id){
        service.remove(Integer.parseInt(id));
    }
    @RequestMapping(value = "/author", method = RequestMethod.GET)
    public List<Author> search(@RequestParam("name")String name){
        return service.findByName(name);
    }
}
