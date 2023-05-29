package com.ultimatum.authcrud.repositories;

import com.ultimatum.authcrud.models.Produit;
import com.ultimatum.authcrud.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProduitRepository extends JpaRepository<Produit, Integer> {

    Optional<Produit> findByUser(User user);

    Page<Produit> findAllByUser(User user, Pageable pageable);
    Optional<Produit> findByNom(String nom);

    Page<Produit> findAllByUserAndNomContainingOrDescriptionContaining(User user, String nom, String Description, Pageable pageable);


    Optional<Produit> findFirstByNomAndUser_IdAndDescriptionAndPrix(String nom, Integer user_id, String Description, String prix);
}
