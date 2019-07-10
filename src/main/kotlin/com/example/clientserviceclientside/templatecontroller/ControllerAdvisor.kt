package com.example.clientserviceclientside.templatecontroller

import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

internal class ClientNotFoundException(id: Int?) : RuntimeException("Could not find employee " + id!!)

@ControllerAdvice
internal class EmployeeNotFoundAdvice {
    private val logger = KotlinLogging.logger {}
    @ResponseBody
    @ExceptionHandler(ClientNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun employeeNotFoundHandler(ex: ClientNotFoundException): String {
        logger.error { ex.message }
        return ex.message.toString()
    }
}