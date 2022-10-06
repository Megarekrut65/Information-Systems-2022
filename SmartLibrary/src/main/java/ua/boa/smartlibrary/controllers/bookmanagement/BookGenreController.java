package ua.boa.smartlibrary.controllers.bookmanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.boa.smartlibrary.dataclasses.bookmanagement.BookGenre;
import ua.boa.smartlibrary.services.bookmanagement.BookGenreService;

import java.util.List;

@RestController
public class BookGenreController {
    @Autowired
    private BookGenreService service;

    @RequestMapping(value = "/book-genre", method = RequestMethod.POST)
    public BookGenre create(@RequestParam("genre-id") String genreId,
                            @RequestParam("book-id") String bookId) {
        return service.create(Integer.parseInt(genreId), Integer.parseInt(bookId));
    }

    @RequestMapping(value = "/book-genre", method = RequestMethod.PUT)
    public BookGenre update(@RequestParam("id") String id, @RequestParam("genre-id") String genreId,
                            @RequestParam("book-id") String bookId) {
        return service.update(Integer.parseInt(id), Integer.parseInt(genreId), Integer.parseInt(bookId));
    }

    @RequestMapping(value = "/book-genres", method = RequestMethod.GET)
    public List<BookGenre> getAll() {
        return service.getAll();
    }

    @RequestMapping(value = "/book-genre", method = RequestMethod.DELETE)
    public void remove(@RequestParam("id") String id) {
        service.remove(Integer.parseInt(id));
    }

    @RequestMapping(value = "/book-genres/by-book-id", method = RequestMethod.GET)
    public List<BookGenre> searchByBook(@RequestParam("book-id") String bookId) {
        return service.findByBook(Integer.parseInt(bookId));
    }

    @RequestMapping(value = "/book-genres/by-genre-id", method = RequestMethod.GET)
    public List<BookGenre> searchByGenre(@RequestParam("genre-id") String genreId) {
        return service.findByGenre(Integer.parseInt(genreId));
    }

    @RequestMapping(value = "/book-genre", method = RequestMethod.GET)
    public BookGenre get(@RequestParam("id") String id) {
        return service.get(Integer.parseInt(id));
    }
}
