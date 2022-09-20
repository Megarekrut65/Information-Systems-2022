package ua.boa.smartlibrary.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.boa.smartlibrary.dataclasses.Tag;
import ua.boa.smartlibrary.services.TagService;

import java.util.List;

@RestController
public class TagController {
    @Autowired
    private TagService service;

    @RequestMapping(value = "/tag", method = RequestMethod.POST)
    public Tag create(@RequestParam("name")String name){
        return service.create(name);
    }
    @RequestMapping(value = "/tag", method = RequestMethod.PUT)
    public Tag update(@RequestParam("id")String id, @RequestParam("name")String name){
        return service.update(Integer.parseInt(id), name);
    }
    @RequestMapping(value = "/tags", method = RequestMethod.GET)
    public List<Tag> getAll(){
        return service.getAll();
    }
    @RequestMapping(value = "/tag", method = RequestMethod.GET)
    public Tag get(@RequestParam("id")String id){
        return service.get(Integer.parseInt(id));
    }
    @RequestMapping(value = "/tag", method = RequestMethod.DELETE)
    public void remove(@RequestParam("id")String id){
        service.remove(Integer.parseInt(id));
    }
    @RequestMapping(value = "/tags/by-name", method = RequestMethod.GET)
    public List<Tag> searchByName(@RequestParam("name")String name){
        return service.findByName(name);
    }
}
