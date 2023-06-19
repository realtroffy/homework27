package by.epam.training.homework27.exception;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.validation.ValidationException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
class GlobalDefaultExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String PATH = "path";
    private static final String STATUS = "status";

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNotFoundException(NoSuchElementException exception) {
        return ResponseEntity.status(NOT_FOUND).body(exception.getMessage());
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<String> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException exc) {
        return ResponseEntity.status(BAD_REQUEST).body("Max upload size of file is " + exc.getMaxUploadSize() + " kB");
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(InvalidFilExtensionException.class)
    public ResponseEntity<String> handleInvalidFilExtensionException(InvalidFilExtensionException exception) {
        return ResponseEntity.status(BAD_REQUEST).body(exception.getMessage());
    }

    @ResponseStatus(CONFLICT)
    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<String> handleUserAlreadyExistsException(
            UserAlreadyExistException exception) {
        return ResponseEntity.status(CONFLICT).body(exception.getMessage());
    }

    @ResponseStatus(CONFLICT)
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<String> handleUsernameNotFoundException(
            UsernameNotFoundException exception) {
        return ResponseEntity.status(UNAUTHORIZED).body(exception.getMessage());
    }

    @ResponseStatus(UNAUTHORIZED)
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> unauthorizedException(BadCredentialsException exception) {
        return ResponseEntity.status(UNAUTHORIZED).body(exception.getMessage());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatus status,
            WebRequest request
    ) {
        Map<String, String> errors = new LinkedHashMap<>();

        String path = request.getDescription(false);

        errors.put(PATH, path);
        errors.put(STATUS, status.getReasonPhrase());

        ex.getBindingResult().getAllErrors()
                .forEach(error -> {
                    String fieldName = ((FieldError) error).getField();
                    String errorMessage = error.getDefaultMessage();
                    errors.put(fieldName, errorMessage);
                });

        return ResponseEntity.status(status).body(errors);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex, @NonNull HttpHeaders headers,
            @NonNull HttpStatus status, WebRequest request) {
        String error = ex.getParameterName() + " parameter is missing";
        return ResponseEntity.status(BAD_REQUEST).body(error);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
            HttpMediaTypeNotSupportedException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatus status,
            WebRequest request) {

        StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType())
                .append(" media type is not supported. Supported media types are ");
        ex.getSupportedMediaTypes().forEach(type -> builder.append(type).append(", "));

        return ResponseEntity.status(UNSUPPORTED_MEDIA_TYPE).body(builder.toString());
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(javax.validation.ConstraintViolationException.class)
    protected ResponseEntity<Object> handleConstraintViolation(
            javax.validation.ConstraintViolationException ex) {
        return ResponseEntity.status(BAD_REQUEST).body(ex.getConstraintViolations());
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    protected ResponseEntity<Object> handleValidationException(ValidationException ex) {
        return ResponseEntity.status(BAD_REQUEST).body(ex.getMessage());
    }

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<String> handleEntityNotFound(EntityNotFoundException ex) {
        return ResponseEntity.status(NOT_FOUND).body(ex.getMessage());
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            NoHandlerFoundException ex, @NonNull HttpHeaders headers,
            @NonNull HttpStatus status, WebRequest request) {

        String message = String.format("Could not find the %s method for URL %s",
                ex.getHttpMethod(), ex.getRequestURL());

        return ResponseEntity.status(BAD_REQUEST).body(message);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<Object> handleDataIntegrityViolation(
            DataIntegrityViolationException ex) {
        if (ex.getCause() instanceof ConstraintViolationException) {
            return ResponseEntity.status(CONFLICT).body(ex.getCause());
        }
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ex);
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex) {

        String message = String.format("The parameter '%s' of value '%s' could not be converted " +
                "to type '%s'", ex.getName(), ex.getValue(), ex.getRequiredType());

        return ResponseEntity.status(BAD_REQUEST).body(message);
    }
}
