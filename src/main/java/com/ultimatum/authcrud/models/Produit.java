package com.ultimatum.authcrud.models;


import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "produit")
public class Produit implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_produit;

    @ManyToOne
    @JoinColumn(name = "id_user", referencedColumnName = "id")
    private User user;
    private String nom;
    private String description;
    private String prix;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime date_in;

    public Produit() {
    }
    @PrePersist
    public void prePersist() {
        date_in = LocalDateTime.now();
    }
    public Integer getId_produit() {
        return id_produit;
    }

    public void setId_produit(Integer id_produit) {
        this.id_produit = id_produit;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrix() {
        return prix;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }

    public LocalDateTime getDate_in() {
        return date_in;
    }

    public void setDate_in(LocalDateTime date_in) {
        this.date_in = date_in;
    }
}
