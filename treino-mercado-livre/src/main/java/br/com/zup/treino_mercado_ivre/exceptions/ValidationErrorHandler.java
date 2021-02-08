package br.com.zup.treino_mercado_ivre.exceptions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ValidationErrorHandler {
    @Autowired
    private MessageSource messageSource;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<ValidationErrorsDto> handlers(MethodArgumentNotValidException exception){
        List<ValidationErrorsDto> list = new ArrayList<>();

        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        fieldErrors.forEach(e->{
            String message = messageSource.getMessage(e, LocaleContextHolder.getLocale());
            ValidationErrorsDto error = new ValidationErrorsDto(e.getField(), message);
            list.add(error);
        });

        return list;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public List<ValidationErrorsDto> bindExceptions(BindException exception){
        List<ValidationErrorsDto> list = new ArrayList<>();

        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        fieldErrors.forEach(e->{
            String message = messageSource.getMessage(e, LocaleContextHolder.getLocale());
            ValidationErrorsDto error = new ValidationErrorsDto(e.getField(), message);
            list.add(error);
        });

        return list;
    }

}
