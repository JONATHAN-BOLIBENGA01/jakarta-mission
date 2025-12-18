package com.jakartaudbl.jakartamission.business;

import jakarta.ejb.Stateless;
import jakarta.ejb.LocalBean;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import com.jakartaudbl.jakartamission.entities.Lieu;

@Stateless
@LocalBean
public class LieuEntrepriseBean {

    @PersistenceContext
    private EntityManager em;

    // Ajouter un lieu
    public void ajouterLieuEntreprise(String nom, String description, double latitude, double longitude) {
        Lieu lieu = new Lieu(nom, description, longitude, latitude);
        em.persist(lieu);
    }

    // Liste complète
    public List<Lieu> listerTousLesLieux() {
        return em.createQuery("SELECT L FROM Lieu L", Lieu.class).getResultList();
    }

    // Modifier un lieu
    public void modifierLieu(int id, String nom, String description, double latitude, double longitude){
        Lieu lieu = em.find(Lieu.class, id);
        if (lieu != null) {
            lieu.setNom(nom);
            lieu.setDescription(description);
            lieu.setLatitude(latitude);
            lieu.setLongitude(longitude);
            em.merge(lieu);
        }
    }
    // Supprimer un lieu
    public void supprimerLieu(int id) {
        Lieu lieu = em.find(Lieu.class, id);
        if (lieu != null) {
            em.remove(lieu);
        }
    }

    // Trouver un lieu par ID
    public Lieu trouverLieuParId(Lieu lieu) {
        return em.find(Lieu.class, lieu);
    }
}