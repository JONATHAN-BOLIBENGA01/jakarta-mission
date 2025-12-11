package com.jakartaudbl.jakartamission.beans;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

@RequestScoped
@Named
public class WelcomBean {

    private String nom;
    private String message;

    private String messageUsd;
    private String messageIdr;

    private double amountUsd;
    private double amountIdr;

    
    private static final double RATE = 16682.0;

    
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getMessage() {
        return message;
    }

   
    public void display() {
        this.message = "Welcome to Indonesia, dear " + this.nom;
    }

    public double getAmountUsd() {
        return amountUsd;
    }

    public void setAmountUsd(double amountUsd) {
        this.amountUsd = amountUsd;
    }

    public double getAmountIdr() {
        return amountIdr;
    }

    public void setAmountIdr(double amountIdr) {
        this.amountIdr = amountIdr;
    }

    public String getMessageUsd() {
        return messageUsd;
    }

    public String getMessageIdr() {
        return messageIdr;
    }

   
    public double convertToUsd() {
        return amountIdr / RATE;
    }

    
    public double convertToIdr() {
        return amountUsd * RATE;
    }

    
    public void processConversion() {
        double usd = convertToUsd();
        double cdf = convertToIdr();

        this.messageUsd = amountIdr + " CDF = " + usd + " USD";
        this.messageIdr = amountUsd + " USD = " + cdf + " CDF";
    }
}
