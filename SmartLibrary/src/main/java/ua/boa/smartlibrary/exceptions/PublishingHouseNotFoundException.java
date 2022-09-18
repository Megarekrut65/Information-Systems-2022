package ua.boa.smartlibrary.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No such publishing house")
public class PublishingHouseNotFoundException extends NotFoundByIdException{

    public PublishingHouseNotFoundException(Integer id) {
        super(id, "Publishing house");
    }
}
