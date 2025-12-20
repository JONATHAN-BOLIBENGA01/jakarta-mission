package com.jakartaudbl.jakartamission.beans;

import com.jakartaudbl.jakartamission.business.UtilisateurEntrepriseBean;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import com.jakartaudbl.jakartamission.entities.Utilisateur;
import com.jakartaudbl.jakartamission.business.SessionManager;

@RequestScoped
@Named
public class WelcomBean {

    private String email;
    private String password;
    private String message;

    public void setEmail(String email) {
        this.email = email;
    }
    @Inject
    private SessionManager sessionManager;

    @Inject
    private UtilisateurEntrepriseBean utilisateurEntrepriseBean;
    
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
    
    

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    
    public String sAuthentifier() {
        Utilisateur utilisateur = utilisateurEntrepriseBean.trouverUtilisateurParEmail(email);

        if(utilisateur != null && utilisateurEntrepriseBean.verifierMotDePasse(password, utilisateur.getPassword())) {
            sessionManager.createSession("user", email);
            return "home?faces-redirect=true";
        }else{
            this.message = "Email ou mot de passe incorrect";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Email ou mot de passe incorrect", null));
            return null;
        }
    }
}
