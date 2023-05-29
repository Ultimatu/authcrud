package com.ultimatum.authcrud.controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    /**
     * Cette méthode est appelée lorsqu'une erreur est survenue dans l'application.
     * @param request : requête HTTP pour récupérer le code d'erreur et l'exception
     * @param model:  le model
     * @return : la vue notfound.html
     */
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Object error = request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);

        if (status != null) { // Si l'erreur est un code d'erreur HTTP (404, 500, etc.)
            Integer statusCode = Integer.valueOf(status.toString());
            model.addAttribute("errorCode", statusCode);
        }

        if (error instanceof Throwable throwable) { // Si l'erreur est une exception
            model.addAttribute("errorMessage", throwable.getMessage());
        }

        return "notfound"; // On retourne la vue notfound.html
    }


}
