/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jakartaudbl.jakartamission.beans;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import java.io.IOException;

/**
 *
 * @author jojo
 */
@Named(value="navigationController")
@RequestScoped
public class NavigationBean {
   public void voirApropos(){
       try{
           String contextPath = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
           FacesContext.getCurrentInstance().getExternalContext()
                   .redirect(contextPath + "/pages/a_propos.xhtml");
       }catch(IOException e){
           
           e.printStackTrace();
       }
   }
   
   public void voirLieu(){
       try{
           String contextPath = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
           FacesContext.getCurrentInstance().getExternalContext()
                   .redirect(contextPath + "/pages/lieux.xhtml");
       }catch(IOException e){
           
           e.printStackTrace();
       }
   } 
   
   public void creerCompte(){
       try{
           String contextPath = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
           FacesContext.getCurrentInstance().getExternalContext()
                   .redirect(contextPath + "/pages/ajoute_utilisateur.xhtml");
       }catch(IOException e){
           
           e.printStackTrace();
       }
   }
}
