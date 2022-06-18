package customer_account_mailSender.msCustomer.controller;

import customer_account_mailSender.msCustomer.model.ErrorDto;
import customer_account_mailSender.msCustomer.model.exception.AccountException;
import customer_account_mailSender.msCustomer.model.exception.CustomerNotFoundException;
import customer_account_mailSender.msCustomer.model.exception.CustomerWithEmailExistException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static customer_account_mailSender.msCustomer.model.constants.ErrorCodes.ACCOUNT_ERROR;
import static customer_account_mailSender.msCustomer.model.constants.ErrorCodes.CUSTOMER_EMAIL_EXISTS;
import static customer_account_mailSender.msCustomer.model.constants.ErrorCodes.CUSTOMER_NOT_FOUND;
import static customer_account_mailSender.msCustomer.model.constants.ErrorCodes.CUSTOMER_UNEXPECTED_ERROR;
import static customer_account_mailSender.msCustomer.model.constants.ErrorCodes.UNEXPECTED_EXCEPTION;
import static org.springframework.http.HttpStatus.FOUND;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<Object> handleCustomerNotFoundException(CustomerNotFoundException ex,
                                                                  WebRequest webRequest) {
        return handleExceptionInternal(ex,
                ErrorDto.builder()
                        .timestamp(LocalDateTime.now())
                        .error(CUSTOMER_NOT_FOUND)
                        .message(ex.getMessage())
                        .build(),
                new HttpHeaders(),
                NOT_FOUND,
                webRequest);
    }

    @ExceptionHandler(CustomerWithEmailExistException.class)
    public ResponseEntity<Object> handleCustomerByEmailExistException(CustomerWithEmailExistException ex,
                                                                      WebRequest webRequest) {
        return handleExceptionInternal(ex,
                ErrorDto.builder()
                        .timestamp(LocalDateTime.now())
                        .error(CUSTOMER_EMAIL_EXISTS)
                        .message(ex.getMessage())
                        .build(),
                new HttpHeaders(),
                FOUND,
                webRequest);
    }

    @ExceptionHandler(AccountException.class)
    public ResponseEntity<Object> handleAccountException(AccountException ex,
                                                         WebRequest webRequest) {
        return handleExceptionInternal(ex,
                ErrorDto.builder()
                        .timestamp(LocalDateTime.now())
                        .error(ACCOUNT_ERROR)
                        .message(ex.getMessage())
                        .build(),
                new HttpHeaders(),
                NOT_FOUND,
                webRequest);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneralException(Exception ex,
                                                         WebRequest webRequest) {
        return handleExceptionInternal(ex,
                ErrorDto.builder()
                        .timestamp(LocalDateTime.now())
                        .error(CUSTOMER_UNEXPECTED_ERROR)
                        .message(ex.getMessage())
                        .build(),
                new HttpHeaders(),
                INTERNAL_SERVER_ERROR,
                webRequest);
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                               HttpHeaders headers,
                                                               HttpStatus status,
                                                               WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return handleExceptionInternal(ex,
                ErrorDto.builder()
                        .timestamp(LocalDateTime.now())
                        .error(UNEXPECTED_EXCEPTION)
                        .message(errors.toString())
                        .build(),
                headers, INTERNAL_SERVER_ERROR, request);
    }
}