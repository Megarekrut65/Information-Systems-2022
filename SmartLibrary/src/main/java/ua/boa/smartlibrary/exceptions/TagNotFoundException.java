package ua.boa.smartlibrary.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No such tag")
public class TagNotFoundException extends NotFoundByIdException{
    public TagNotFoundException(Integer id) {
        super(id, "Tag");
    }
}
