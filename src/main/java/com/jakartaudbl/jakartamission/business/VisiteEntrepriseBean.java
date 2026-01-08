/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jakartaudbl.jakartamission.business;
import jakarta.ejb.Stateless;
import jakarta.ejb.LocalBean;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import com.jakartaudbl.jakartamission.entities.Visite;
import java.util.List;

/**
 *
 * @author jojo
 */

@Stateless
@LocalBean
public class VisiteEntrepriseBean {

        @PersistenceContext(unitName = "indonesiaPU")
    private EntityManager em;

    @Transactional
    public void enregistrerVisite(Visite visite) {
        em.persist(visite);
    }

    public List<Visite> listerToutesLesVisites() {
        return em.createQuery("SELECT v FROM Visite v ORDER BY v.dateVisite DESC", Visite.class).getResultList();
    }

    public List<Visite> listerVisitesParUtilisateur(Long utilisateurId) {
        return em.createQuery("SELECT v FROM Visite v WHERE v.utilisateur.id = :uId ORDER BY v.dateVisite DESC", Visite.class)
                .setParameter("uId", utilisateurId)
                .getResultList();
    }
}
