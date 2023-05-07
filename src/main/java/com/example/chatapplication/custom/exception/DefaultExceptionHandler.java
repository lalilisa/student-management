package com.example.chatapplication.custom.exception;



import com.example.chatapplication.dto.error.ErrorMessage;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.zalando.problem.spring.web.advice.ProblemHandling;


import java.util.Date;

//@ControllerAdvice
public class DefaultExceptionHandler implements ProblemHandling {



    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<ErrorMessage> problemHandlingException(GeneralException problem, WebRequest request) {
        ErrorMessage message = ErrorMessage.builder()
//                .statusCode(problem.getStatus().getStatusCode())
                .errorCode(problem.getCode())
                .timestamp(new Date())
                .message(problem.getMessage())
                .messageParams(problem.getMessageParams())
                .description(request.getDescription(false))
                .build();

        return new ResponseEntity<ErrorMessage>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> globalExceptionHandler(Exception ex, WebRequest request) {
        ErrorMessage message = ErrorMessage.builder()
//                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .errorCode(ex.getLocalizedMessage())
                .timestamp(new Date())
                .message(ex.getLocalizedMessage())
                .description(request.getDescription(false))
                .build();

        return new ResponseEntity<ErrorMessage>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AbstractProblem.class)
    public ResponseEntity<ErrorMessage> problemHandlingException(AbstractProblem problem, WebRequest request) {
        ErrorMessage message = ErrorMessage.builder()
//                .statusCode(problem.getStatus().getStatusCode())
                .errorCode(problem.getTitle())
                .timestamp(new Date())
                .message(problem.getDetail())
                .description(request.getDescription(false))
                .build();

        return new ResponseEntity<ErrorMessage>(message, HttpStatus.valueOf(problem.getStatus().getStatusCode()));
    }
}