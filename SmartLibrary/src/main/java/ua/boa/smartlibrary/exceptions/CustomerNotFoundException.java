package ua.boa.smartlibrary.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No such customer")
public class CustomerNotFoundException extends NotFoundByIdException{
    public CustomerNotFoundException(Integer id) {
        super(id, "Customer");
    }
}
