package ua.boa.smartlibrary.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.boa.smartlibrary.dataclasses.BookInfo;

public interface BookInfoRepository extends JpaRepository<BookInfo, Integer> {
}
