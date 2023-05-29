package com.ultimatum.authcrud.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    private HttpSession session;

    /**
     * Fonction d'affichage de la page d'accueil
     * @return  : return index
     */
    @GetMapping("index")
    public String index(Model model) {
        session.removeAttribute("user");
        return "index";
    }

    /**
     * rediriger à la page d'accueil
     * @param model : le model
     * @return : redirect à la page d'accueil
     */
    @GetMapping("")
    public String redirect(Model model){
        session.removeAttribute("user");
        return "redirect:index";
    }
}
