package ua.boa.smartlibrary.db.repositories.bookmanagement;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.boa.smartlibrary.dataclasses.bookmanagement.Book;
import ua.boa.smartlibrary.dataclasses.bookmanagement.BookTag;
import ua.boa.smartlibrary.dataclasses.bookmanagement.Tag;

import java.util.List;

public interface BookTagRepository extends JpaRepository<BookTag, Integer> {
    List<BookTag> findBookTagsByBook(Book book);
    List<BookTag> findBookTagsByTag(Tag tag);
}
