package hello.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IdNotFoundException extends Exception {

    public IdNotFoundException(Long id){
        super("Field with id " + id + " not found");
    }
}
