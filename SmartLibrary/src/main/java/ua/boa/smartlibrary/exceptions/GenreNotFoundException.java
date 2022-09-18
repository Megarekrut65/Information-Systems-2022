package ua.boa.smartlibrary.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No such genre")
public class GenreNotFoundException extends NotFoundByIdException {
    public GenreNotFoundException(Integer id) {
        super(id, "Genre");
    }
}
