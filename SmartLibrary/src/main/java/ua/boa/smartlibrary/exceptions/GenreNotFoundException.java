package ua.boa.smartlibrary.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No such genre")
public class GenreNotFoundException extends RuntimeException {
    private final Integer id;
    @Override
    public String getMessage() {
        return "Genre with id=" + id + " not found!";
    }

    public GenreNotFoundException(Integer id) {
        this.id = id;
    }
}
