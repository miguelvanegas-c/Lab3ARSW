package edu.eci.arsw.blueprints.response;

import edu.eci.arsw.blueprints.persistence.exception.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.exception.BlueprintPersistenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(BlueprintNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleResponsibleNotFound(BlueprintNotFoundException ex) {
        return response(ex, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BlueprintPersistenceException.class)
    public ResponseEntity<ApiResponse<?>> handleResponsibleBadRequest(BlueprintPersistenceException ex){
        return response(ex, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ApiResponse<?>> response(Exception ex, HttpStatus status){
        var apiResponse = new ApiResponse<String>(status.value(),"An exception occurred during execution.",ex.getMessage());
        return new ResponseEntity<>(apiResponse, status);
    }

}
