package com.jakartaudbl.jakartamission.business;

import jakarta.ejb.Stateless;
import jakarta.ejb.LocalBean;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;
import com.jakartaudbl.jakartamission.entities.Utilisateur;

@Stateless
@LocalBean
public class UtilisateurEntrepriseBean {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void ajouterUtilisateurEntreprise(String username, String email, String password, String description) {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        Utilisateur utilisateur = new Utilisateur(username, email, hashedPassword, description);
        em.persist(utilisateur);
    }
    
    public boolean verifierMotDePasse(String password, String hashedPassword) { 
        return BCrypt.checkpw(password, hashedPassword); 
    }  

    public List<Utilisateur> listerTousLesUtilisateurs() {
        return em.createQuery("SELECT u FROM Utilisateur u", Utilisateur.class).getResultList();
    }

    @Transactional
    public void supprimerUtilisateur(Long id) {
        Utilisateur utilisateur = em.find(Utilisateur.class, id);
        if (utilisateur != null) {
            em.remove(utilisateur);
        }
    }

    public Utilisateur trouverUtilisateurParId(Long id) {
        return em.find(Utilisateur.class, id);
    }

    public Utilisateur trouverUtilisateurParEmail(String email) {
        try {
            return em.createQuery("SELECT u FROM Utilisateur u WHERE u.email = :email", Utilisateur.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
    
    public boolean utilisateurExiste(String username, String email) {
        try {
            Long count = em.createQuery(
                "SELECT COUNT(u) FROM Utilisateur u WHERE u.username = :username OR u.email = :email", 
                Long.class)
                .setParameter("username", username)
                .setParameter("email", email)
                .getSingleResult();
            return count > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean authentifier(String email, String password) {
        Utilisateur utilisateur = trouverUtilisateurParEmail(email);
        if (utilisateur != null && verifierMotDePasse(password, utilisateur.getPassword())) {
            return true;
        }
        return false;
    }

    @Transactional
    public void mettreAJourProfil(Utilisateur utilisateur) {
        em.merge(utilisateur);
    }

    @Transactional
    public boolean changerMotDePasse(Long userId, String ancienMotDePasse, String nouveauMotDePasse) {
        Utilisateur utilisateur = em.find(Utilisateur.class, userId);
        if (utilisateur != null && verifierMotDePasse(ancienMotDePasse, utilisateur.getPassword())) {
            String hashedPassword = BCrypt.hashpw(nouveauMotDePasse, BCrypt.gensalt());
            utilisateur.setPassword(hashedPassword);
            em.merge(utilisateur);
            return true;
        }
        return false;
    }
}
