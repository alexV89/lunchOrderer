package hello.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TryingToUpdateIdException extends  Exception {

    public TryingToUpdateIdException(String message){
        super("Id field in " + message + " cannot be updated");
    }
}
