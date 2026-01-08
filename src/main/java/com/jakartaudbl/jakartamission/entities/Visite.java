/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jakartaudbl.jakartamission.entities;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author jojo
 */

@Entity
@Table(name = "VISITE")
public class Visite implements Serializable {
        @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "UTILISATEUR_ID", nullable = false)
    private Utilisateur utilisateur;

    @ManyToOne
    @JoinColumn(name = "LIEU_ID", nullable = false)
    private Lieu lieu;

    @Column(name = "DATE_VISITE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateVisite;

    @Column(name = "TEMPS_PASSE")
    private Double tempsPasse;

    @Column(name = "OBSERVATION", length = 1000)
    private String observation;

    @Column(name = "DEPENSE")
    private Double depense;

    public Visite() {
        this.dateVisite = new Date();
    }

    public Visite(Utilisateur utilisateur, Lieu lieu) {
        this.utilisateur = utilisateur;
        this.lieu = lieu;
        this.dateVisite = new Date();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Lieu getLieu() {
        return lieu;
    }

    public void setLieu(Lieu lieu) {
        this.lieu = lieu;
    }

    public Date getDateVisite() {
        return dateVisite;
    }

    public void setDateVisite(Date dateVisite) {
        this.dateVisite = dateVisite;
    }

    public Double getTempsPasse() {
        return tempsPasse;
    }

    public void setTempsPasse(Double tempsPasse) {
        this.tempsPasse = tempsPasse;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public Double getDepense() {
        return depense;
    }

    public void setDepense(Double depense) {
        this.depense = depense;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Visite)) {
            return false;
        }
        Visite other = (Visite) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.jakartaudbl.jakartamission.entities[ id=" + id + " ]";
    }
}
