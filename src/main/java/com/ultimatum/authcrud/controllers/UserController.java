package com.ultimatum.authcrud.controllers;

import com.ultimatum.authcrud.models.User;
import com.ultimatum.authcrud.repositories.UserRepository;
import com.ultimatum.authcrud.services.EmailService;
import com.ultimatum.authcrud.services.UserServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Random;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired //injection de dépendance
    private UserRepository userRepository;


    @Autowired //injection de dépendance
    private UserServiceImpl userService;

    @Autowired
    private EmailService emailService;

    /**
     * Fonction d'affichage de la page d'inscription
     * @param model : model de l'utilisateur
     * @return : redirection vers la page d'inscription
     */
    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    /**
     * Fonction d'inscription
     * @param user : utilisateur
     * @param model : model de l'utilisateur
     * @param session : session de l'utilisateur
     * @return : redirection vers le dashboard
     */
    @PostMapping("/signup/send-code")
    public String signup(User user, Model model, HttpSession session) {
        if (userService.getByEmail(user.getEmail())) {
            model.addAttribute("error", "Email existe déjà");
            return "signup";
        }
        session.setAttribute("user", user);
        String to = (String) user.getEmail();
        String code = generateRandomCode(6);
        String subject = "Code de vérification";
        String body = "<!DOCTYPE html>"
                + "<html lang=\"en\">"
                + "<head>"
                + "<meta charset=\"UTF-8\">"
                + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"
                + "<title>Code de vérification</title>"
                + "</head>"
                + "<body>"
                + "<h2>Code de vérification</h2>"
                + "<h1>Votre code de vérification est : <strong>" + code + "</strong></h1>"
                + "</body>"
                + "</html>";
        
        if ( emailService.sendEmail(to, subject, body)){
            session.setAttribute("code", code);
            return "redirect:/user/signup/verify-code";
        }
        return "redirect:/user/signup";
    }
    @GetMapping("/signup/verify-code")
    public String verifyCode(Model model, HttpSession session) {
        String code = (String) session.getAttribute("code");
        String error = (String) session.getAttribute("error");
        String code_of_form = (String) session.getAttribute("code_of_form");
        if (error != null) {
            model.addAttribute("error", error);
            session.removeAttribute("error");
        }
        if (code_of_form != null) {
            model.addAttribute("code_of_form", code_of_form);
            session.removeAttribute("code_of_form");
        }
        if (code != null) {
            model.addAttribute("code", code);
            return "user/verify-code";
        }
        return "redirect:/user/signup";
    }

    @PostMapping("/signup/verify-code")
    public String verifyCodeFinal(Model model, @RequestParam("code") String code, HttpSession session) {
        String sessionCode = (String) session.getAttribute("code");
        if (sessionCode != null && sessionCode.equals(code)) {
            User user = (User) session.getAttribute("user");
            String res = userService.signup(user);
            if (res.equals("ok")){
                session.removeAttribute("code");
                session.setAttribute("user", user);
                model.addAttribute("user", user);
                return "redirect:/produit/dashboard";
            }
            model.addAttribute("error", res);
            model.addAttribute("user", user);
            return "redirect:/user/signup";
        }
        session.setAttribute("error", "Code de vérification invalide");
        session.setAttribute("code_of_form", code);
        return "redirect:/user/signup/verify-code";
    }

    /**
     * Fonction d'affichage de la page de connexion
     * @param model : model de l'utilisateur
     * @return : redirection vers la page de connexion
     */
    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    /**
     * Fonction de connexion
     * @param user : utilisateur
     * @param model : model de l'utilisateur
     * @param session : session de l'utilisateur
     * @return : redirection vers le dashboard
     */
    @PostMapping("/login")
    public String login(User user, Model model, HttpSession session) {
        if (userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword()).isPresent()) {
            model.addAttribute("user", user);
            session.setAttribute("user", userService.getByEmailAndPassword(user.getEmail(), user.getPassword()));
            return "redirect:/produit/dashboard";
        }
        model.addAttribute("errorlog", "Email ou mot de passe incorrect");
        model.addAttribute("user", user);
        return "login";
    }

    /**
     * Fonction d'affichage de la page d'édition des informations de l'utilisateur
     * @param session  : session de l'utilisateur
     * @return : redirection vers la page d'édition
     */
    @GetMapping("/edit")
    public String edit(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        return "user/edit";
    }

    /**
     * Fonction de modification des informations de l'utilisateur
     * @param user : utilisateur
     * @param model : model de l'utilisateur
     * @param session : session de l'utilisateur
     * @return : redirection vers la page de profil
     */
    @PostMapping("/update")
    public String update(User user, Model model, HttpSession session) {
        User user1 = (User) session.getAttribute("user");
        user.setId(user1.getId());
        String res = userService.update(user);
        if (res.equals("ok")){
            session.setAttribute("user", user);
            model.addAttribute("user", user);
            return "redirect:/user/profile";
        }
        model.addAttribute("error", res);
        model.addAttribute("user", user);
        return "redirect:/user/profile";
    }

    /**
     * Fonction d'affichage de la page de profil
     * @param model : model de l'utilisateur
     * @param session : session de l'utilisateur
     * @return : redirection vers la page de profil
     */
    @GetMapping("/profile")
    public String profile(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        return "user/profile";
    }

    /**
     *  Fonction de déconnexion
     * @param session : session de l'utilisateur
     * @return : redirection vers la page d'accueil
     */
    @GetMapping("/logout/true")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/index";
    }

    /**
     *  Fonction de déconnexion
     * @param model : model de l'utilisateur
     * @param session : session de l'utilisateur
     * @return : redirection vers la page d'accueil
     */
    @GetMapping("/logout")
    public String logoutValidation(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        return "user/logout";
    }

    /**
     * Fonction d'affichage de la page de récupération de mot de passe
     * @param model : model de l'utilisateur
     * @return : redirection vers la page de récupération de mot de passe
     */
    @GetMapping("/password-recovery")
    public String passReset(Model model, HttpSession session) {
    
        boolean display_forMail = session.getAttribute("display_forMail") != null && (boolean) session.getAttribute("display_forMail");
        boolean display_codeValidator = session.getAttribute("display_codeValidator") != null && (boolean) session.getAttribute("display_codeValidator");
        boolean display_newPasswordForm = session.getAttribute("display_newPasswordForm") != null && (boolean) session.getAttribute("display_newPasswordForm");

        if (display_forMail) {
            model.addAttribute("display_forMail", true);
            model.addAttribute("display_codeValidator", false);
            model.addAttribute("display_newPasswordForm", false);
        } else if (display_codeValidator) {
            model.addAttribute("display_forMail", false);
            model.addAttribute("display_codeValidator", true);
            model.addAttribute("display_newPasswordForm", false);
        } else if (display_newPasswordForm) {
            model.addAttribute("display_forMail", false);
            model.addAttribute("display_codeValidator", false);
            model.addAttribute("display_newPasswordForm", true);
        } else {
            model.addAttribute("display_forMail", true);
            model.addAttribute("display_codeValidator", false);
            model.addAttribute("display_newPasswordForm", false);
        }

        session.removeAttribute("display_forMail");
        session.removeAttribute("display_codeValidator");
        session.removeAttribute("display_newPasswordForm");

        String error = (String) session.getAttribute("error");
        if (error != null) {
            model.addAttribute("error", error);
            session.removeAttribute("error");
        }

        String success = (String) session.getAttribute("success");
        if (success != null) {
            model.addAttribute("success", success);
            session.removeAttribute("success");
        }

        return "user/password-recovery";
    }

    /**
     * Fonction de génération d'un code aléatoire
     * @param length : longueur du code
     * @return : code généré
     */
    @PostMapping("/password/send-code")
    public String sendCode(Model model, @RequestParam("email") String email, HttpSession session) {

        if (userService.getByEmail(email)) {
            // Générer un code de réinitialisation aléatoire de 6 caractères
            String code = generateRandomCode(6);

            // Stocker le code dans la variable de session
            session.setAttribute("resetCode", code);
            session.setAttribute("email", email);

            //Envoi de code de verification par email
            String subject = "Code de réinitialisation de mot de passe";

            String body = "<!DOCTYPE html>"
                    + "<html lang=\"en\">"
                    + "<head>"
                    + "<meta charset=\"UTF-8\">"
                    + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"
                    + "<title>Code de réinitialisation de mot de passe</title>"
                    + "</head>"
                    + "<body>"
                    + "<h2>Code de réinitialisation de mot de passe</h2>"
                    + "<h1>Votre code de réinitialisation est : <strong>" + code + "</strong></h1>"
                    + "</body>"
                    + "</html>";

            if (emailService.sendEmail(email, subject, body)){
                session.setAttribute("display_forMail", false);
                session.setAttribute("display_codeValidator", true);
                session.setAttribute("email", email);
                return "redirect:/user/password-recovery";
            }
            return "redirect:/user/password-recovery";
        }

        session.setAttribute("error", "Aucun utilisateur trouvé");
        return "redirect:/user/password-recovery";
    }

    /**
     *  Fonction de vérification du code de réinitialisation
     * @param model : model de l'utilisateur
     * @param code : code de réinitialisation
     * @param session : session de l'utilisateur
     * @return : redirection vers la page d'accueil
     */
    @GetMapping("/password/verify-code")
    public String verifyCode(Model model, @RequestParam("code") String code, HttpSession session) {
        String sessionCode = (String) session.getAttribute("resetCode");

        if (sessionCode != null && sessionCode.equals(code)) {
            session.setAttribute("display_newPasswordForm", true);

            return "redirect:/user/password-recovery";
        }

        session.setAttribute("error", "Code de réinitialisation invalide");
        session.setAttribute("display_codeValidator", true);


        return "redirect:/user/password-recovery";
    }

    /**
     *  Fonction de réinitialisation du mot de passe
     * @param model : model de l'utilisateur
     * @param password : nouveau mot de passe
     * @param session : session de l'utilisateur
     * @return : redirection vers la page d'accueil
     */
    @PostMapping("/password/reset")
    public String resetPasswordFinal(Model model, @RequestParam("password") String password, HttpSession session) {
        String email = (String) session.getAttribute("email");
        String sessionCode = (String) session.getAttribute("resetCode");
        System.out.println("sessionCode  in reset: " + sessionCode);
        System.out.println("Email in reset: "+ email);
        if (sessionCode != null) {
            // Réinitialiser le mot de passe de l'utilisateur
            if (userService.getByEmail(email)) {
                User user = userService.getDataByEmail(email);

                // Mettre à jour le mot de passe dans la base de données
                user.setPassword(password);
                userService.update(user);

                // Supprimer le code de réinitialisation de la variable de session
                session.removeAttribute("resetCode");
                session.removeAttribute("email");

                session.setAttribute("success", "Mot de passe réinitialisé avec succès");
                return "redirect:/user/redirect-to-login";
            }

            session.setAttribute("error", "Aucun utilisateur trouvé");
            return "redirect:/user/password-recovery";
        }

        session.setAttribute("error", "Code de réinitialisation invalide");
        session.setAttribute("display_newPasswordForm", true);
        session.setAttribute("email", email);

        return "redirect:/user/password-recovery";


    }

    /**
     * Méthode pour rediriger l'utilisateur vers la page de connexion
     * @param model : le modèle de la vue
     * @param session : la session de l'utilisateur
     * @return : la page de connexion
     */
    @GetMapping("/redirect-to-login")
    public String redirectToLogin(Model model, HttpSession session){

        String message = (String) session.getAttribute("success");
        if (message.isEmpty()){
            return "redirect:/user/login";
        }
        model.addAttribute("success", message);
        return "user/redirector";

    }



    /**
     * Méthode pour générer un code aléatoire
     * @param length : la longueur du code
     * @return : le code généré
     */
    private String generateRandomCode(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder code = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            code.append(characters.charAt(index));
        }

        return code.toString();
    }



}
