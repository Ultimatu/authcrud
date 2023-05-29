package com.ultimatum.authcrud.controllers;

import com.ultimatum.authcrud.models.Produit;
import com.ultimatum.authcrud.models.User;
import com.ultimatum.authcrud.services.ProduitServiceImpl;
import com.ultimatum.authcrud.services.UserServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@Controller
@RequestMapping("/produit")
public class ProduitController {

    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private ProduitServiceImpl produitService;

    /**
     * Fonction de redirection vers le dashboard
     * @param model : le model
     * @param session : la session
     * @return : la vue dashboard.html
     */
    @GetMapping("/dashboard")
    public String gotoDashboard(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        System.out.println("session :"+user);
        if (user == null || user.getEmail() == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", user);
        return "produit/dashboard";
    }

    /**
     * Fonction d'ajout de produit
     * @param model : le model
     * @param session : la session
     * @return : la vue add.html
     */
    @GetMapping("/add")
    public String add(Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if (user == null || user.getEmail() == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", user);

        return "produit/add";
    }

    /**
     *  Ajout de produit
     * @param model : le model
     * @param session : la session
     * @param page : la page courante
     * @return : la vue list.html
     */
    @GetMapping("/list")
    public String listProduits(Model model, HttpSession session, @RequestParam(defaultValue = "0") int page) {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getEmail() == null) {
            return "redirect:/login";
        }
        PageRequest pageable = PageRequest.of(page , 10);
        Page<Produit> produits = produitService.getAll(user, pageable);

        if (!produits.isEmpty()){
            model.addAttribute("user", user);
            model.addAttribute("produits", produits.getContent());
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", produits.getTotalPages());
        }
        else {
            model.addAttribute("vide", "aucun produits");
        }

        return "produit/list";
    }


    /**
     *  Modification de produit
     * @param id : l'id du produit
     * @param model : le model de la vue
     * @param session : la session
     * @return : la vue edit.html
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") int id, Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if (user == null || user.getEmail() == null) {
            return "redirect:/login";
        }
        Produit produit = produitService.getById(id).get();
        model.addAttribute("user", user);
        model.addAttribute("produit", produit);
        model.addAttribute("id_produit", id);

        return "produit/edit";
    }

    /**
     *  Recherche de produit par mot clé
     * @param keyword : le mot clé de recherche
     * @param model : le model de la vue
     * @param session : la session
     * @param page : la page courante
     * @return : la vue list.html
     */
    @GetMapping("/search")
    public String search(@RequestParam("keyword") String keyword, Model model, HttpSession session, @RequestParam(defaultValue = "0") int page){

        User user = (User) session.getAttribute("user");
        if (user == null || user.getEmail() == null) {
            return "redirect:/login";
        }
        PageRequest pageable = PageRequest.of(page , 10);
        Page<Produit> produits = produitService.getByKeyword(user, keyword, keyword, pageable);
        model.addAttribute("produits", produits.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", produits.getTotalPages());
        model.addAttribute("keyword", keyword);
        model.addAttribute("user", user);

        return "produit/list";
    }

    /**
     *  Suppression de produit
     * @param id : l'id du produit
     * @param model : le model de la vue
     * @param session : la session
     * @return : la vue delete.html
     */
    @GetMapping("/delete/{id}")
    public String deleteProduit(@PathVariable("id") int id, Model model, HttpSession session){
        Optional<Produit> produit = produitService.getById(id);
        model.addAttribute("produit", produit);
        return "produit/delete";
    }

    /**
     *  Ajout de produit dans la base
     * @param produit : le produit à ajouter
     * @param model : le model de la vue
     * @param session : la session
     * @return : la vue list.html
     */
    @PostMapping("/add")
    public String save(@ModelAttribute("produit") Produit produit, Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if (user == null || user.getEmail() == null) {
            return "redirect:/login";
        }
        User user1 = userService.getById(user.getId());
        model.addAttribute("user", user);
        produit.setUser(user1);
        String res = produitService.saveProduit(produit);
        if (res.equals("ok")){
            return "redirect:/produit/list";
        }
        model.addAttribute("error", res);
        return "redirect:/produit/add";
    }

    /**
     *  Modification de produit
     * @param produit : le produit à modifier
     * @param model : le model de la vue
     * @param session : la session
     * @return : la vue list.html
     */
    @PostMapping("/update")
    public String update(@ModelAttribute("produit") Produit produit, Model model, HttpSession session){

        User user = (User) session.getAttribute("user");
        if (user == null || user.getEmail() == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", user);
        produit.setUser(user);
        produitService.updateProduit(produit);

        return "redirect:/produit/list";
    }

    /**
     *  Suppression de produit
     * @param id : l'id du produit
     * @param model : le model de la vue
     * @param session : la session
     * @return : la vue list.html
     */
    @PostMapping("/delete/{id}")
    public String deleteProduitConf(@PathVariable("id") int id, Model model, HttpSession session){

        User user = (User) session.getAttribute("user");
        if (user == null || user.getEmail() == null) {
            return "redirect:/login";
        }
        String result = produitService.deleteProduit(id);
        if (result.equals("success")){
            return "redirect:/produit/list";
        }

        model.addAttribute("user", user);
        model.addAttribute("error", result);
        return "redirect:/produit/list";
    }

}
