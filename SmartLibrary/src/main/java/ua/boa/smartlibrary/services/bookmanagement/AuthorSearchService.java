package ua.boa.smartlibrary.services.bookmanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.boa.smartlibrary.PageGetter;
import ua.boa.smartlibrary.dataclasses.bookmanagement.Author;
import ua.boa.smartlibrary.dataclasses.bookmanagement.Authorship;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AuthorSearchService extends PageGetter<Author> {
    @Autowired
    private AuthorService authorService;
    @Autowired
    private AuthorshipService authorshipService;
    @Autowired
    private BookService bookService;

    public List<Author> findAuthorByAll(String authorName, String bookTitle) {
        List<Author> authors = authorName.equals("") ? authorService.getAll() : authorService.findByName(authorName);
        if (!bookTitle.equals("")) {
            List<Authorship> authorships = authorshipService
                    .findByAuthorsAndBooks(authors, bookService.findByTitle(bookTitle));
            authors.clear();
            authorships.forEach(authorship -> authors.add(authorship.getAuthor()));
        }
        Set<Author> set = new HashSet<>(authors);
        authors.clear();
        authors.addAll(set);
        return authors;
    }
    public List<Author> getPage(int page, int perPage, List<Author> authors){
        return getPart(authors, page, perPage, Comparator.comparing(Author::getName));
    }
}
