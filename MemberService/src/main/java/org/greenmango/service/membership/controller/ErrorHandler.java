package org.greenmango.service.membership.controller;

import org.greenmango.service.membership.constants.ErrorCode;
import org.greenmango.service.membership.constants.StatusCode;
import org.greenmango.service.membership.exception.DataErrorException;
import org.greenmango.service.membership.ui.dto.BaseResponse;
import org.greenmango.service.membership.ui.dto.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import javax.servlet.ServletException;
import java.util.Locale;
import java.util.Optional;

/**
 * Global error handling functions . localisation added to message.properties
 */
@RestControllerAdvice
public class ErrorHandler {
    private static Logger logger = LoggerFactory.getLogger(ErrorHandler.class);
    MessageSource messageSource;

    @Autowired
    public ErrorHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /** Display error message based on error code
     * @param exception DataErrorException
     * @return ResponseEntity
     */
    @ExceptionHandler(DataErrorException.class)
    public ResponseEntity<BaseResponse> handleDataErrorExeption(DataErrorException exception){
        String message = Optional.of( messageSource.getMessage("error.code." +
                exception.getErrorCode().getErrorCode(), null, Locale.ENGLISH ))
                .filter(predicate -> {
                    return !predicate.equalsIgnoreCase("error.code." +
                            exception.getErrorCode().getErrorCode());
                })
                .orElseGet( () -> exception.getErrorCode().getErrorDescription() );
        HttpStatus httpStatus = exception.getErrorCode()==ErrorCode.ELEMENT_NOT_FIND ? HttpStatus.NOT_FOUND:
                HttpStatus.INTERNAL_SERVER_ERROR;
        Status status = new Status(StatusCode.ERROR.getCode(), exception.getErrorCode().getErrorCode(), message);
        return new ResponseEntity<BaseResponse>(new BaseResponse(null, status),httpStatus);
    }

    /**
     * @param exception HttpMessageNotReadableException data fromat errors
     * @return ResponseEntity
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<BaseResponse> ptherErrors(HttpMessageNotReadableException exception){
        logger.error("system error", exception);
        Status status = new Status(StatusCode.ERROR.getCode(), ErrorCode.SYSTEM_ERROR.getErrorCode(),
                ErrorCode.FORMAT_EXCEPTION.getErrorDescription());
        return new ResponseEntity<BaseResponse>(new BaseResponse(null, status), HttpStatus.BAD_REQUEST);

    }

    /** any servlet exception return System error
     * @param exception ServletException
     * @return ResponseEntity
     */
    @ExceptionHandler(ServletException.class)
    public ResponseEntity<BaseResponse> ptherErrors(ServletException exception){
        logger.error("system error", exception);
        Status status = new Status(StatusCode.ERROR.getCode(), ErrorCode.SYSTEM_ERROR.getErrorCode(),
                ErrorCode.SYSTEM_ERROR.getErrorDescription());
        return new ResponseEntity<BaseResponse>(new BaseResponse(null, status), HttpStatus.BAD_REQUEST);

    }

}
