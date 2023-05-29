package com.ultimatum.authcrud.services;


import com.ultimatum.authcrud.models.Produit;
import com.ultimatum.authcrud.models.User;
import com.ultimatum.authcrud.repositories.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProduitServiceImpl  implements ProduitService{
    @Autowired
    private  ProduitRepository produitRepository;

    /**
     * Fonction de sauvegarde d'un produit
     * @param produit : le produit à sauvegarder
     * @return : un message de succès ou d'erreur
     */
    @Override
    public String saveProduit(Produit produit) {
        Optional<Produit> optionalProduit = produitRepository.findFirstByNomAndUser_IdAndDescriptionAndPrix(
                produit.getNom(),
                produit.getUser().getId(),
                produit.getDescription(),
                produit.getPrix()
        );
        Produit produit1 = new Produit();
        // Vérifie si le produit existe déjà en comparant les attributs
        if (optionalProduit.isPresent()) {
            return "Error : Produit déjà existant";
        }
        produit1.setNom(produit.getNom());
        produit1.setUser(produit.getUser());
        produit1.setDescription(produit.getDescription());
        produit1.setDate_in(produit.getDate_in());
        produit1.setPrix(produit.getPrix());
        produitRepository.save(produit1);
        return "ok";
    }


    /**
     * Fonction de récupération de tous les produits
     * @return : la liste des produits
     */
    @Override
    public List<Produit> getAllByUserId(Integer id) {
        return getAllByUserId(id);
    }


    public Optional<Produit> getById(Integer id) {
        return produitRepository.findById(id);
    }

    @Override
    public String updateProduit(Produit produit) {
        Optional<Produit> optionalProduit = produitRepository.findById(produit.getId_produit());
        if (optionalProduit.isPresent()) {
            Produit prod = optionalProduit.get();
            prod.setNom(produit.getNom());
            prod.setDescription(produit.getDescription());
            prod.setPrix(produit.getPrix());
            prod.setDate_in(LocalDateTime.now());
            produitRepository.save(prod);
            return "Success: mise à jour de produit effectuée avec succès";
        } else {
            return "Error: mise à jour de produit échouée";
        }
    }


    @Override
    public String deleteProduit(Integer id) {
        Optional<Produit> optProduit = produitRepository.findById(id);
        if (optProduit.isPresent()) {
            produitRepository.delete(optProduit.get());
            return "success";
        } else {
            return "Suppression échouée";
        }
    }

    @Override
    public Page<Produit> getAll(User user, Pageable pageable) {
        return produitRepository.findAllByUser(user, pageable);
    }



    @Override
    public Page<Produit> getByKeyword(User user, String keyword, String keyword1,  Pageable pageable) {
        return  produitRepository.findAllByUserAndNomContainingOrDescriptionContaining(user, keyword, keyword1, pageable);
    }
}
