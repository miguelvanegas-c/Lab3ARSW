package edu.eci.arsw.blueprints.response;

import edu.eci.arsw.blueprints.persistence.exception.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.exception.BlueprintPersistenceException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(BlueprintNotFoundException.class)
    public ApiResponse<?> handleResponsibleNotFound(RuntimeException ex) {
        return response(ex, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BlueprintPersistenceException.class)
    public ApiResponse<?> handleResponsibleBadRequest(RuntimeException ex){
        return response(ex, HttpStatus.BAD_REQUEST);
    }

    private ApiResponse<?> response(RuntimeException ex, HttpStatus status){
        return new ApiResponse<String>(status.value(),"An exception occurred during execution.",ex.getMessage());
    }

}
