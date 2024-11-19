package com.example.tpchuang.restfulwebservices.exception;

import com.example.tpchuang.restfulwebservices.user.UserNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Customized response entity exception handler that formats all thrown exceptions.
 */
@ControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

  /**
   * Handles all exceptions.
   *
   * @param ex      the exception to handle
   * @param request the web request
   * @return a response entity with the error details
   */
  @ExceptionHandler(Exception.class)
  public final ResponseEntity<ErrorDetails> handleAllExceptions(Exception ex, WebRequest request) {
    ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(),
        request.getDescription(false));
    return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  /**
   * Handles user not found exceptions.
   *
   * @param ex      the exception to handle
   * @param request the web request
   * @return a response entity with the error details
   */
  @ExceptionHandler(UserNotFoundException.class)
  protected final ResponseEntity<ErrorDetails> handleUserNotFoundExceptions(Exception ex,
      WebRequest request) {
    ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(),
        request.getDescription(false));
    return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
  }

  /**
   * Handles method argument not valid exceptions.
   *
   * @param ex      the exception to handle
   * @param headers the headers
   * @param status  the status
   * @param request the web request
   * @return a response entity with the error details
   */
  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
      HttpHeaders headers, HttpStatusCode status, WebRequest request) {
    List<String> allErrors = ex.getFieldErrors().stream()
        .map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
    String message = String.format("%s validation error(s): %s.", allErrors.size(),
        String.join(". ", allErrors));
    ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), message,
        request.getDescription(false));
    return handleExceptionInternal(
        ex, errorDetails, headers, HttpStatus.UNPROCESSABLE_ENTITY, request);
  }
}
