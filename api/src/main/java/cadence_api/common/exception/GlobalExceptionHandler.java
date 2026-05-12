package cadence_api.common.exception;


import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

//    404 Not Found
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String,Object>> handleUserNotFound(
            UserNotFoundException ex){
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(HabitNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleHabitNotFound(
            HabitNotFoundException ex){
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(LogNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleLogNotFound(
            LogNotFoundException ex){
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

//    409 - Conflict
    @ExceptionHandler(DuplicateLogException.class)
    public ResponseEntity<Map<String, Object>> handleDuplicateLog(
            DuplicateLogException ex){
        return buildResponse(HttpStatus.CONFLICT, ex.getMessage());
    }

//    400 - Validation errors
    public ResponseEntity<Map<String, Object>> handleValidation(
            MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(e->e.getField() + ": " + e.getDefaultMessage())
                .collect(java.util.stream.Collectors.joining(","));
        return buildResponse(HttpStatus.BAD_REQUEST, message);
    }

//    500 - catch all

    public ResponseEntity<Map<String,Object>> handleGeneral(Exception ex){
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong: " +ex.getMessage());
    }
//    Constraint Violation
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> handleDataIntegrity(
            DataIntegrityViolationException ex) {
        return buildResponse(HttpStatus.CONFLICT,
                "Data integrity violation — duplicate or invalid data");
    }








    private ResponseEntity<Map<String, Object>> buildResponse(HttpStatus status, String message) {
        Map<String, Object> body = new HashMap<>();
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);
        body.put("timestamp", LocalDateTime.now());
        return ResponseEntity.status(status).body(body);
    }



}
