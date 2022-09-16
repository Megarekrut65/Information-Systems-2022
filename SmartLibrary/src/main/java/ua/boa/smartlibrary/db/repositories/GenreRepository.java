package ua.boa.smartlibrary.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.boa.smartlibrary.dataclasses.Genre;

public interface GenreRepository extends JpaRepository<Genre, Integer> {
}
