package com.accommodation.service

class ValidationException(val validationErrors: List<String>) : RuntimeException("Validation failed")
