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
import com.jakartaudbl.jakartamission.entities.Utilisateur;
import com.jakartaudbl.jakartamission.business.SessionManager;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;

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

    private String loginEmail;
    private String loginPassword;
    private Utilisateur currentUser;

    private String ancienMotDePasse;
    private String nouveauMotDePasse;
    private String confirmerNouveauMotDePasse;

    @Inject
    private UtilisateurEntrepriseBean utilisateurEntrepriseBean;

    @Inject
    private SessionManager sessionManager;

    public String getUsername() {
        if (username == null || username.isEmpty()) {
            Utilisateur user = getCurrentUser();
            if (user != null) {
                return user.getUsername();
            }
        }
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
        if (description == null) {
            Utilisateur user = getCurrentUser();
            if (user != null) {
                return user.getDescription();
            }
        }
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLoginEmail() {
        return loginEmail;
    }

    public void setLoginEmail(String loginEmail) {
        this.loginEmail = loginEmail;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    public Utilisateur getCurrentUser() {
        if (currentUser == null) {
            String email = sessionManager.getValueFromSession("user");
            if (email != null) {
                currentUser = utilisateurEntrepriseBean.trouverUtilisateurParEmail(email);
            }
        }
        return currentUser;
    }

    public void setCurrentUser(Utilisateur currentUser) {
        this.currentUser = currentUser;
    }
    
   
    public String ajouterUtilisateur(){
        if (password == null || !password.equals(confirmPassword)) {
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "Les mots de passe ne correspondent pas"));
            return null;
        }
        
        try {
            if (utilisateurEntrepriseBean.utilisateurExiste(username, email)) {
                FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "Utilisateur ou email déjà existant"));
                return null;
            }
            utilisateurEntrepriseBean.ajouterUtilisateurEntreprise(username, email, password, description);
            System.out.println("Utilisateur ajouté : " + username + " - " + email);
            
            // Add success message for the next page
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Succès", "Votre compte a été créé avec succès !"));
            
            return "/index?faces-redirect=true";
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur critique", "Une erreur est survenue lors de la création du compte : " + e.getMessage()));
            e.printStackTrace();
            return null;
        }
    }

    public String seConnecter() {
        if (utilisateurEntrepriseBean.authentifier(loginEmail, loginPassword)) {
            sessionManager.createSession("user", loginEmail);
            currentUser = utilisateurEntrepriseBean.trouverUtilisateurParEmail(loginEmail);
            System.out.println("Utilisateur connecté : " + currentUser.getUsername());
            return null; // Stay on the same page
        } else {
            System.out.println("Échec de la connexion pour : " + loginEmail);
            return null;
        }
    }

    public String seDeconnecter() {
        sessionManager.invalidateSession();
        currentUser = null;
        loginEmail = null;
        loginPassword = null;
        return null;
    }

    public boolean isLoggedIn() {
        return getCurrentUser() != null;
    }

    public String getAncienMotDePasse() {
        return ancienMotDePasse;
    }

    public void setAncienMotDePasse(String ancienMotDePasse) {
        this.ancienMotDePasse = ancienMotDePasse;
    }

    public String getNouveauMotDePasse() {
        return nouveauMotDePasse;
    }

    public void setNouveauMotDePasse(String nouveauMotDePasse) {
        this.nouveauMotDePasse = nouveauMotDePasse;
    }

    public String getConfirmerNouveauMotDePasse() {
        return confirmerNouveauMotDePasse;
    }

    public void setConfirmerNouveauMotDePasse(String confirmerNouveauMotDePasse) {
        this.confirmerNouveauMotDePasse = confirmerNouveauMotDePasse;
    }

    public String mettreAJourProfil() {
        Utilisateur user = getCurrentUser();
        if (user != null) {
            if (username != null && !username.trim().isEmpty()) {
                user.setUsername(username);
            }
            if (description != null) {
                user.setDescription(description);
            }
            utilisateurEntrepriseBean.mettreAJourProfil(user);
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Succès", "Votre profil a été mis à jour"));
        }
        return null;
    }

    public String changerMotDePasse() {
        if (nouveauMotDePasse == null || !nouveauMotDePasse.equals(confirmerNouveauMotDePasse)) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "Les nouveaux mots de passe ne correspondent pas"));
            return null;
        }

        Utilisateur user = getCurrentUser();
        if (user != null) {
            boolean success = utilisateurEntrepriseBean.changerMotDePasse(user.getId(), ancienMotDePasse, nouveauMotDePasse);
            if (success) {
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Succès", "Votre mot de passe a été modifié"));
                ancienMotDePasse = null;
                nouveauMotDePasse = null;
                confirmerNouveauMotDePasse = null;
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "L'ancien mot de passe est incorrect"));
            }
        }
        return null;
    }

    public String deconnecter() {
        sessionManager.invalidateSession();
        currentUser = null;
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_INFO, "Succès", "Vous avez été déconnecté"));
        return "/index?faces-redirect=true";
    }
}
