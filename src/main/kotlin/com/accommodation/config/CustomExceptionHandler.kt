package com.accommodation.config

import com.accommodation.service.ValidationException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class CustomExceptionHandler {

    @ExceptionHandler(ValidationException::class)
    fun handleValidationException(ex: ValidationException): ResponseEntity<Map<String, List<String>>> {
        val response: Map<String, List<String>> = mapOf("validationErrors" to ex.validationErrors)
        return ResponseEntity.badRequest().body(response)
    }
}