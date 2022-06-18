package customer_account_mailSender.msAccount.controller;

import customer_account_mailSender.msAccount.model.ErrorDto;
import customer_account_mailSender.msAccount.model.exception.AccountNotFoundException;
import customer_account_mailSender.msAccount.model.exception.AccountNumberExistException;
import customer_account_mailSender.msAccount.model.exception.CustomerException;
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

import static customer_account_mailSender.msAccount.model.constants.ErrorCodes.ACCOUNT_NOT_FOUND;
import static customer_account_mailSender.msAccount.model.constants.ErrorCodes.ACCOUNT_NUMBER_EXISTS;
import static customer_account_mailSender.msAccount.model.constants.ErrorCodes.ACCOUNT_UNEXPECTED_ERROR;
import static customer_account_mailSender.msAccount.model.constants.ErrorCodes.CUSTOMER_ERROR;
import static customer_account_mailSender.msAccount.model.constants.ErrorCodes.UNEXPECTED_EXCEPTION;
import static org.springframework.http.HttpStatus.FOUND;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomerException.class)
    public ResponseEntity<Object> handleCustomerException(CustomerException ex,
                                                          WebRequest webRequest) {
        return handleExceptionInternal(ex,
                ErrorDto.builder()
                        .timestamp(LocalDateTime.now())
                        .error(CUSTOMER_ERROR)
                        .message(ex.getMessage())
                        .build(),
                new HttpHeaders(),
                NOT_FOUND,
                webRequest);
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<Object> handleAccountNotFoundException(AccountNotFoundException ex,
                                                                 WebRequest webRequest) {
        return handleExceptionInternal(ex,
                ErrorDto.builder()
                        .timestamp(LocalDateTime.now())
                        .error(ACCOUNT_NOT_FOUND)
                        .message(ex.getMessage())
                        .build(),
                new HttpHeaders(),
                NOT_FOUND,
                webRequest);
    }

    @ExceptionHandler(AccountNumberExistException.class)
    public ResponseEntity<Object> handleAccountNumberExistException(AccountNumberExistException ex,
                                                                    WebRequest webRequest) {
        return handleExceptionInternal(ex,
                ErrorDto.builder()
                        .timestamp(LocalDateTime.now())
                        .error(ACCOUNT_NUMBER_EXISTS)
                        .message(ex.getMessage())
                        .build(),
                new HttpHeaders(),
                FOUND,
                webRequest);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneralException(Exception ex,
                                                         WebRequest webRequest) {
        return handleExceptionInternal(ex,
                ErrorDto.builder()
                        .timestamp(LocalDateTime.now())
                        .error(ACCOUNT_UNEXPECTED_ERROR)
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