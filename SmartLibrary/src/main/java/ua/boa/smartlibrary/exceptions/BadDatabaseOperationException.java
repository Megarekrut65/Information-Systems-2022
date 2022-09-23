package ua.boa.smartlibrary.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE, reason = "Bad operation")
public class BadDatabaseOperationException extends RuntimeException{
    public BadDatabaseOperationException(String message) {
        super(message);
    }
}
