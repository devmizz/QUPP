package com.qupp.config;

import com.qupp.user.controller.dto.response.Response;
import com.qupp.exception.ImageUploadException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.util.NoSuchElementException;

@RestControllerAdvice
@RestController
public class CustomExceptionHandler {

    // 400 BadRequest
    @ExceptionHandler({
            MethodArgumentNotValidException.class
    })
    public ResponseEntity<Response> methodArgumentNotValidException(final MethodArgumentNotValidException e) {

        BindingResult bindingResult = e.getBindingResult();

        StringBuilder sb = new StringBuilder();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            sb.append("[");
            sb.append(fieldError.getRejectedValue());
            sb.append("](은)는 ");
            sb.append(fieldError.getDefaultMessage());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        Response.builder()
                                .msg(sb.toString())
                                .build()
                );
    }

    // 400 BadRequest
    @ExceptionHandler({
            ImageUploadException.class,
            IllegalArgumentException.class,
            IOException.class
    })
    public ResponseEntity<Response> BadRequest(final Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        Response.builder()
                                .msg(e.getMessage())
                                .build()
                );
    }

    // 401 Unauthorized
    @ExceptionHandler({
            BadCredentialsException.class,
            UnsupportedJwtException.class,
            ExpiredJwtException.class,
            MalformedJwtException.class

    })
    public ResponseEntity<Response> Unauthorized(final Exception e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(
                        Response.builder()
                                .msg(e.getMessage())
                                .build()
                );
    }

    // 404 NotFound
    @ExceptionHandler({
            NoSuchElementException.class
    })
    public ResponseEntity<Response> notFound(final Exception e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(
                        Response.builder()
                                .msg(e.getMessage())
                                .build()
                );
    }

    // 500 Internal Server Error
    @ExceptionHandler({
            Exception.class
    })
    public ResponseEntity Exception(final Exception e) {
        return ResponseEntity.internalServerError()
                .body(
                        Response.builder()
                                .msg(e.getMessage())
                                .build()
                );
    }
}