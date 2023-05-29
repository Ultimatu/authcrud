package com.ultimatum.authcrud.models;


import jakarta.persistence.*;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nom;
    private String prenom;

    @Column(unique = true)
    private String email;
    private String password;
    private LocalDateTime sign_date = LocalDateTime.now();

    public LocalDateTime getSign_date() {
        return sign_date;
    }

    public void setSign_date(LocalDateTime sign_date) {
        this.sign_date = sign_date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
