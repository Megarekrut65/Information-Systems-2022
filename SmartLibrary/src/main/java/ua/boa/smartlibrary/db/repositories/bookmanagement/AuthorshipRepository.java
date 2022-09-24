package ua.boa.smartlibrary.db.repositories.bookmanagement;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.boa.smartlibrary.dataclasses.bookmanagement.Author;
import ua.boa.smartlibrary.dataclasses.bookmanagement.Authorship;
import ua.boa.smartlibrary.dataclasses.bookmanagement.Book;

import java.util.List;

public interface AuthorshipRepository extends JpaRepository<Authorship, Integer> {
    List<Authorship> findAuthorshipsByBook(Book book);

    List<Authorship> findAuthorshipsByAuthor(Author author);
}
