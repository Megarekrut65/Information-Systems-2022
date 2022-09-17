package ua.boa.smartlibrary.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No such author")
public class AuthorNotFoundException extends RuntimeException {
    private final Integer id;
    @Override
    public String getMessage() {
        return "Author with id=" + id + " not found!";
    }

    public AuthorNotFoundException(Integer id) {
        this.id = id;
    }
}