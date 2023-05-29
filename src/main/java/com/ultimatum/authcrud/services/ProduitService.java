package com.ultimatum.authcrud.services;

import com.ultimatum.authcrud.models.Produit;
import com.ultimatum.authcrud.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ProduitService {

    public String saveProduit(Produit produit);

    public List<Produit> getAllByUserId(Integer id);

    public Optional<Produit> getById(Integer id);

    public String updateProduit(Produit produit);

    public String deleteProduit(Integer id);

    public Page<Produit> getAll(User user, Pageable pageable);

    public Page<Produit> getByKeyword(User user, String keyword, String keyword1, Pageable pageable);

}
