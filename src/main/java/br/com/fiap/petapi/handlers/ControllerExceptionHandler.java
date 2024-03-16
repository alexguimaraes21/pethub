package br.com.fiap.petapi.handlers;

import br.com.fiap.petapi.responsemodel.ErrorMessageResponseModel;
import br.com.fiap.petapi.responsemodel.ErrorResponseModel;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.coyote.BadRequestException;
import org.springdoc.api.ErrorMessage;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { IOException.class })
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorMessage> ioException(IOException e, WebRequest request) {

        ErrorMessage message = new ErrorMessage(e.getMessage());
        return new ResponseEntity<ErrorMessage>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    protected ErrorResponseModel handleBadRequestException(BadRequestException ex, HttpServletRequest request) {
        ErrorResponseModel errorResponseModel = new ErrorResponseModel();
        errorResponseModel.setTimestamp(LocalDateTime.now());
        errorResponseModel.setStatus(400);
        errorResponseModel.setMessage("Bad Request");

        List<ErrorMessageResponseModel> errors = new ArrayList<>();
        ErrorMessageResponseModel errorMessageResponse = new ErrorMessageResponseModel();
        errorMessageResponse.setMessage(ex.getMessage());
        errors.add(errorMessageResponse);

        errorResponseModel.setErrors(errors);
        errorResponseModel.setPath(request.getServletPath());
        return errorResponseModel;
    }

//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(MissingServletRequestParameterException.class)
//    protected ErrorResponseModel handleMissingServletRequestParameterException(
//            MissingServletRequestParameterException ex,
//            HttpServletRequest request
//    ) {
//        ErrorResponseModel errorResponseModel = new ErrorResponseModel();
//        errorResponseModel.setTimestamp(LocalDateTime.now());
//        errorResponseModel.setStatus(400);
//        errorResponseModel.setError("Bad Request");
//
//        List<ErrorMessageResponseModel> errors = new ArrayList<>();
//        ErrorMessageResponseModel errorMessageResponse = new ErrorMessageResponseModel();
//        errorMessageResponse.setMessage(ex.getMessage());
//        errors.add(errorMessageResponse);
//
//        errorResponseModel.setErrorMessage(errors);
//        errorResponseModel.setPath(request.getServletPath());
//        return errorResponseModel;
//    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ErrorResponseModel handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException ex,
            HttpServletRequest request
    ) {
        ErrorResponseModel errorResponseModel = new ErrorResponseModel();
        errorResponseModel.setTimestamp(LocalDateTime.now());
        errorResponseModel.setStatus(400);
        errorResponseModel.setMessage("Bad Request");

        List<ErrorMessageResponseModel> errors = new ArrayList<>();
        ErrorMessageResponseModel errorMessageResponse = new ErrorMessageResponseModel();
        errorMessageResponse.setMessage(ex.getMessage());
        errors.add(errorMessageResponse);

        errorResponseModel.setErrors(errors);
        errorResponseModel.setPath(request.getServletPath());
        return errorResponseModel;
    }

//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(MethodArgumentNotValidException.class)
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        List<String> errorList = ex
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        ErrorResponseModel errorResponseModel = new ErrorResponseModel();
        errorResponseModel.setTimestamp(LocalDateTime.now());
        errorResponseModel.setStatus(400);
        errorResponseModel.setMessage(ex.getLocalizedMessage());

//        List<ErrorMessageResponseModel> errors = new ArrayList<>();
//        ErrorMessageResponseModel errorMessageResponse = new ErrorMessageResponseModel();
//        errorMessageResponse.setMessage(ex.getMessage());
//        errors.add(errorMessageResponse);

        errorResponseModel.setErrors(errorList);
        return handleExceptionInternal(ex, errorResponseModel, headers, status, request);
    }
}
