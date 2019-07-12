package com.example.clientserviceclientside.templatecontroller

import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

internal class ClientNotFoundException(srn: String?) : RuntimeException("Could not find employee by surname $srn")

internal class ClientMismatchedFormException():RuntimeException("""Some form details
    | might be missing or initialized with a placeholder!""".trimMargin())

@ControllerAdvice
internal class ClientNotFoundAdvice {
    private val logger = KotlinLogging.logger {}
    @ResponseBody
    @ExceptionHandler(ClientNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun clientNotFoundHandler(ex: ClientNotFoundException): String {
        logger.error { ex.message }
        return ex.message.toString()
    }
    @ResponseBody
    @ExceptionHandler(ClientMismatchedFormException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun clientMismatchedFormHandler(ex: ClientMismatchedFormException): String {
        logger.error { ex.message }
        return ex.message.toString()
    }
}