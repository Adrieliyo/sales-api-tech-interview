package com.macronnect.sales_api.exception;

import com.macronnect.sales_api.exception.client.ClientNotFoundException;
import com.macronnect.sales_api.exception.client.EmailAlreadyExistsException;
import com.macronnect.sales_api.exception.sale.InsufficientStockException;
import com.macronnect.sales_api.exception.sale.SaleAlreadyCancelledException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.*;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new LinkedHashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.put(error.getField(), error.getDefaultMessage()));

        return ResponseEntity.badRequest().body(errors);
    }

    /**
     * Recursos no encontrados (404)
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(
            ResourceNotFoundException ex) {

        return buildErrorResponse(
                HttpStatus.NOT_FOUND,
                ex.getMessage());
    }

    /**
     * Recursos duplicados (409)
     */
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateResource(
            DuplicateResourceException ex) {

        return buildErrorResponse(
                HttpStatus.CONFLICT,
                ex.getMessage());
    }

    /**
     * Errores inesperados (500)
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(
            Exception ex) {

        ex.printStackTrace();

        return buildErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "An unexpected error occurred.");
    }

    /**
     * Manejo de ventas que ya fueron canceladas (409)
     */
    @ExceptionHandler(SaleAlreadyCancelledException.class)
    public ResponseEntity<ErrorResponse> handleSaleAlreadyCancelled(
            SaleAlreadyCancelledException ex) {

        return buildErrorResponse(
                HttpStatus.CONFLICT,
                ex.getMessage());

    }

    /**
     * Manejo de stock insuficiente (400)
     */
    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<ErrorResponse> handleInsufficientStock(
            InsufficientStockException ex) {

        return buildErrorResponse(
                HttpStatus.BAD_REQUEST,
                ex.getMessage());

    }

    /**
     * Método auxiliar para construir respuestas de error
     */
    private ResponseEntity<ErrorResponse> buildErrorResponse(
            HttpStatus status,
            String message) {

        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                message);

        return ResponseEntity.status(status).body(error);
    }
}
