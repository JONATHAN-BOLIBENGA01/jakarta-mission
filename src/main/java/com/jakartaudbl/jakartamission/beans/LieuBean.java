/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jakartaudbl.jakartamission.beans;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.jakartaudbl.jakartamission.entities.Lieu;
import com.jakartaudbl.jakartamission.business.LieuEntrepriseBean;
import jakarta.faces.event.AjaxBehaviorEvent;
import jakarta.ws.rs.client.Client;


/**
 *
 * @author jojo
 */
import jakarta.faces.event.AjaxBehaviorEvent;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.MediaType;
@Named(value = "lieuBean")
@ViewScoped
public class LieuBean implements Serializable {
    private String nom;
    private String description;
    private double longitude;
    private double latitude;
    private String weatherMessage;
    private Integer selectedLieu;
    private int id; // ID for editing
    private int idASupprimer; // ID for deletion
    private boolean showModal = false;
    private List<Lieu> lieux = new ArrayList<>();

    @Inject
    private LieuEntrepriseBean lieuEntrepriseBean;

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }

    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public boolean isShowModal() { return showModal; }
    public void setShowModal(boolean showModal) { this.showModal = showModal; }

    public List<Lieu> getLieux() { return lieuEntrepriseBean.listerTousLesLieux(); }

    public void ajouterLieu() {
        if (nom != null && !nom.isEmpty() && description != null && !description.isEmpty()) {
            if (id == 0) {
                lieuEntrepriseBean.ajouterLieuEntreprise(nom, description, latitude, longitude);
            } else {
                lieuEntrepriseBean.modifierLieu(id, nom, description, latitude, longitude);
                id = 0; // Reset ID after update
            }
            viderFormulaire();
        }
    }

    public void supprimer(int id) {
        lieuEntrepriseBean.supprimerLieu(id);
    }

    public void preparerSuppression(int id) {
        this.idASupprimer = id;
        this.showModal = true;
    }

    public void confirmerSuppression() {
        if (idASupprimer != 0) {
            lieuEntrepriseBean.supprimerLieu(idASupprimer);
            idASupprimer = 0;
        }
        this.showModal = false;
    }

    public void annulerSuppression() {
        this.idASupprimer = 0;
        this.showModal = false;
    }

    public void preparerModification(Lieu lieu) {
        this.id = lieu.getId();
        this.nom = lieu.getNom();
        this.description = lieu.getDescription();
        this.latitude = lieu.getLatitude();
        this.longitude = lieu.getLongitude();
    }

    public void viderFormulaire() {
        this.id = 0;
        this.nom = "";
        this.description = "";
        this.latitude = 0.0;
        this.longitude = 0.0;
    }
    public void fetchWeatherMessage(Lieu l) {
      
        if (selectedLieu != null) {
            // Appel au service web pour obtenir les données météorologiques
        
            String serviceURL = "http://localhost:8080/j-weather/webapi/JakartaWeather?latitude="
                    + l.getLatitude() + "&longitude=" + l.getLongitude();
            Client client = ClientBuilder.newClient();
            String response = client.target(serviceURL)
                    .request(MediaType.TEXT_PLAIN)
                    .get(String.class);
            // Enregistrement du message météo dans la variable weatherMessage
          this.weatherMessage =response;
        }
       
    }
    
    public void updateWeatherMessage(AjaxBehaviorEvent event) {
        
        Lieu lieu=lieuEntrepriseBean.trouverLieuParId(selectedLieu);
        this.fetchWeatherMessage(lieu);
    } 
 public String getWeatherMessage() {
        return weatherMessage;
    }
    public void setWeatherMessage(String weatherMessage) {
        this.weatherMessage = weatherMessage;
    }
}
