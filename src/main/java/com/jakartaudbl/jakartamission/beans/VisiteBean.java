package com.jakartaudbl.jakartamission.beans;

import com.jakartaudbl.jakartamission.business.LieuEntrepriseBean;
import com.jakartaudbl.jakartamission.business.VisiteEntrepriseBean;
import com.jakartaudbl.jakartamission.entities.Lieu;
import com.jakartaudbl.jakartamission.entities.Visite;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Named(value = "visiteBean")
@ViewScoped
public class VisiteBean implements Serializable {

    @Inject
    private VisiteEntrepriseBean visiteEntrepriseBean;

    @Inject
    private UtilisateurBean utilisateurBean;

    @Inject
    private LieuEntrepriseBean lieuEntrepriseBean;

    private Integer selectedLieuId;
    private Visite newVisite = new Visite();
    private List<Visite> recentVisites;
    private boolean formVisible = false;

    @PostConstruct
    public void init() {
        String lieuIdParam = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("lieuId");
        if (lieuIdParam != null) {
            try {
                selectedLieuId = Integer.parseInt(lieuIdParam);
                preparerVisite(selectedLieuId);
            } catch (NumberFormatException e) {
                // Ignore
            }
        }
        chargerRecentVisites();
    }

    public void chargerRecentVisites() {
        if (utilisateurBean.isLoggedIn()) {
            recentVisites = visiteEntrepriseBean.listerVisitesParUtilisateur(utilisateurBean.getCurrentUser().getId().longValue());
        }
    }

    public void preparerVisite(Integer lieuId) {
        Lieu lieu = lieuEntrepriseBean.trouverLieuParId(lieuId);
        if (lieu != null) {
            newVisite = new Visite();
            newVisite.setLieu(lieu);
            newVisite.setUtilisateur(utilisateurBean.getCurrentUser());
            newVisite.setDateVisite(new Date());
            formVisible = true;
        }
    }

    public String enregistrerVisite() {
        try {
            visiteEntrepriseBean.enregistrerVisite(newVisite);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Succès", "Visite enregistrée avec succès !"));
            newVisite = new Visite();
            formVisible = false;
            chargerRecentVisites();
            return null; // Stay on the same page to see the updated list
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", "Impossible d'enregistrer la visite : " + e.getMessage()));
            return null;
        }
    }

    public void annulerVisite() {
        newVisite = new Visite();
        formVisible = false;
    }

    // Getters and Setters
    public Integer getSelectedLieuId() { return selectedLieuId; }
    public void setSelectedLieuId(Integer selectedLieuId) { this.selectedLieuId = selectedLieuId; }

    public Visite getNewVisite() { return newVisite; }
    public void setNewVisite(Visite newVisite) { this.newVisite = newVisite; }

    public List<Visite> getRecentVisites() { return recentVisites; }

    public boolean isFormVisible() { return formVisible; }
    public void setFormVisible(boolean formVisible) { this.formVisible = formVisible; }
}
