package com.ultimatum.authcrud.exception;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpServerErrorException;

import java.lang.annotation.Annotation;

@ControllerAdvice
public class GlobalExceptionHandler implements  ExceptionHandler{

    /**
     * Fonction de redirection vers la page d'erreur 404
     * @return : la vue notfound.html
     */
    @ExceptionHandler(value = {ChangeSetPersister.NotFoundException.class})
    public String handleNotFoundException(Model model) {
        return ("notfound");
    }

    /**
     * Fonction de redirection vers la page d'erreur 500
     * @return : la vue notfound.html
     */
    @ExceptionHandler(value = {HttpServerErrorException.InternalServerError.class})
    public String handleInternalServerError(Model model) {
        return ("notfound");
    }

    /**
     * Fonction de redirection vers la page d'erreur 500
     * @return : la vue notfound.html
     */
    @ExceptionHandler(value = {Exception.class})
    public String handleException(Model model) {
        return ("notfound");
    }

    /**
     * Fonction de redirection vers la page d'erreur 500
     * @return : la vue notfound.html
     */
    @ExceptionHandler(value = {RuntimeException.class})
    public String handleRuntimeException(Model model) {
        return ("notfound");
    }

    /**
     * Fonction de redirection vers la page d'erreur 500
     * @return : la vue notfound.html
     */
    @ExceptionHandler(value = {Throwable.class})
    public String handleThrowable(Model model) {
        return ("notfound");
    }

    @Override
    public Class<? extends Throwable>[] value() {
        return new Class[0];
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }
}
