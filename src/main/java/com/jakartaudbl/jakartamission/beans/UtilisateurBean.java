/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jakartaudbl.jakartamission.beans;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import com.jakartaudbl.jakartamission.business.UtilisateurEntrepriseBean;

/**
 *
 * @author jojo
 */
@Named("utilisateurBean")
@RequestScoped
public class UtilisateurBean implements Serializable {
    private String username;
    private String email;
    private String password;
    private String confirmPassword;
    private String description;

    @Inject
    private UtilisateurEntrepriseBean utilisateurEntrepriseBean;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
   
    public String ajouterUtilisateur(){
        if (password == null || !password.equals(confirmPassword)) {
            System.out.println("Les mots de passe ne correspondent pas");
            return null;
        }
        
        if (utilisateurEntrepriseBean.utilisateurExiste(username, email)) {
            System.out.println("Utilisateur ou email déjà existant");
            return null;
        }
        utilisateurEntrepriseBean.ajouterUtilisateurEntreprise(username, email, password, description);
        System.out.println("Utilisateur ajouté : " + username + " - " + email);
        return "/index?faces-redirect=true";
    }
}
