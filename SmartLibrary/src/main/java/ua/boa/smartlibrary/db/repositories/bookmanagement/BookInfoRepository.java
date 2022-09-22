package ua.boa.smartlibrary.db.repositories.bookmanagement;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.boa.smartlibrary.dataclasses.bookmanagement.BookInfo;

public interface BookInfoRepository extends JpaRepository<BookInfo, Integer> {
}
