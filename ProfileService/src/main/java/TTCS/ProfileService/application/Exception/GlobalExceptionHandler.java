package TTCS.ProfileService.application.Exception;

import KMA.TTCS.CommonService.Exception.AxonExceptionCom;
import TTCS.ProfileService.application.Exception.AppException.AppException;
import TTCS.ProfileService.application.Exception.AxonException.AxonException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.axonserver.connector.command.AxonServerNonTransientRemoteCommandHandlingException;
import org.axonframework.axonserver.connector.command.AxonServerRemoteCommandHandlingException;
import org.axonframework.commandhandling.CommandExecutionException;
import org.axonframework.modelling.command.AggregateNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Date;
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({MethodArgumentNotValidException.class , ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationException(Exception exception , WebRequest webRequest){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(new Date());
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setPath(webRequest.getDescription(false).replace("uri=" , ""));
        errorResponse.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());
        String  message = exception.getMessage();
        if (exception instanceof MethodArgumentNotValidException){
            int start =  message.lastIndexOf("[");
            int end = message.lastIndexOf("]]");
            message = message.substring(start+1 ,end );
            errorResponse.setError("Payload invalid");
        } else if (exception instanceof ConstraintViolationException){
            int start =  message.lastIndexOf(".");
            int end = message.length();
            message = message.substring(start+1 ,end );
            errorResponse.setError("Parameter invalid");

        }
        errorResponse.setMessage(message);
        return errorResponse;

    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleInternalException(Exception exception , WebRequest webRequest){
        System.out.println("Handle handleInternalException");
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(new Date());
        errorResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorResponse.setPath(webRequest.getDescription(false).replace("uri=" , ""));
        errorResponse.setError(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        if(exception instanceof  MethodArgumentTypeMismatchException){
            errorResponse.setMessage("Failed to convert value of type");
        }
        return errorResponse;

    }



    @ExceptionHandler(AppException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleAppException(AppException e, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(new Date());
        errorResponse.setPath(request.getDescription(false).replace("uri=", ""));
        errorResponse.setStatus(e.getErrorCode().getCode());
        errorResponse.setError(AppException.class.getSimpleName());
        errorResponse.setMessage(e.getMessage());
        return errorResponse;
    }

    @ExceptionHandler(AxonException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleAxonException(AxonException e, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(new Date());
        errorResponse.setPath(request.getDescription(false).replace("uri=", ""));
        errorResponse.setStatus(e.getErrorCode().getCode());
        errorResponse.setError(AxonException.class.getSimpleName());
        errorResponse.setMessage(e.getMessage());
        return errorResponse;
    }

//    @ExceptionHandler(AxonServerRemoteCommandHandlingException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ErrorResponse handleAxonException2(AxonServerRemoteCommandHandlingException e, WebRequest request) {
//        ErrorResponse errorResponse = new ErrorResponse();
//        errorResponse.setTimestamp(new Date());
//        errorResponse.setPath(request.getDescription(false).replace("uri=", ""));
//        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
//        errorResponse.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());
//        errorResponse.setMessage(e.getMessage());
//        return errorResponse;
//    }
    @ExceptionHandler(CommandExecutionException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleAxonException(AxonServerRemoteCommandHandlingException e, WebRequest request) {
        String message = e.getMessage();
        int start =  message.lastIndexOf(": ");
        int end = message.length();
        message = message.substring(start+1 ,end );
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(new Date());
        errorResponse.setPath(request.getDescription(false).replace("uri=", ""));
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());
        errorResponse.setMessage(message);
        return errorResponse;
    }







}
