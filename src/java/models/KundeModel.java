/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import db.KundePersistence;
import entities.Kunde;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Katha
 */
@Dependent
public class KundeModel implements Serializable{
    @EJB
    private KundePersistence kundePersistence;
    
    @Inject
    SchrankController schrankController;

    public void neuerKunde(Kunde kunde){
        kundePersistence.persist(kunde);
    }
    
    public KundeModel(){
    }
    
    public void initSchrankController(){
        schrankController.initSchrankList();
    }
    
    public List<Kunde> eingecheckteKunden(){
        List<Kunde> ergebnis = new ArrayList<>();
        for(Kunde k:this.kundePersistence.findEingecheckteKunden()){
            ergebnis.add(k);
        }
        return ergebnis;
    }
    
    
    public String checkIn(int kundennummer){
        Kunde kunde = this.kundePersistence.findKundeByKundennummer(kundennummer);
        if(kunde == null){
            return "Es existiert kein Kunde mit der eingegebenen Kundennummer (" + kundennummer + ")";
        }
        else if(kunde.isEingecheckt()){
            return "Kunde ist bereits eingecheckt";
        }
        else{
            //SchrankController schrankController = new SchrankController();
            
            int schranknummer = schrankController.einchecken(kundennummer);
            if(schranknummer != -1){
                this.kundePersistence.einchecken(kundennummer);
                return "Kunde erfolgreich eingecheckt. Schranknummer: " + schranknummer;
            }else{
                return "Kein Schrank mehr verfügbar";
            }
        }
    }
    
    public void kundenBearbeiten(Kunde kunde){
        kundePersistence.merge(kunde);
    }
    
    public String kundenSuchErgebnis(int kundennummer){
        if(kundePersistence.findAll().isEmpty()){
            return "Es existiert noch kein Kunde.";
        }
        else if(kundePersistence.findKundeByKundennummer(kundennummer) == null){
            return "Es existiert kein Kunde mit der eingegebenen Kundennummer (" + kundennummer + ")";
        }
        else{
            return kundePersistence.findKundeByKundennummer(kundennummer).toString();
        }
    }
    
    public Kunde kundenSuchen(int kundennummer){
        return kundePersistence.findKundeByKundennummer(kundennummer);
    }
   
    public String checkOut(long id){
        if(kundePersistence.findAll().isEmpty()){
            return "Es existiert noch kein Kunde.";
        }
        else if(kundePersistence.findKundeById(id) == null){
            return "Es existiert kein Kunde mit der eingegebenen Kundennummer (" + id + ")";
        }
        else if(!kundePersistence.findKundeById(id).isEingecheckt()){
            return "Kunde ist nicht eingecheckt.";
        }
        else{
            kundePersistence.auschecken(id);
            return "Kunde erfolgreich ausgecheckt";
        }
    }

    public KundePersistence getKundePersistence() {
        return kundePersistence;
    }

    public void setKundePersistence(KundePersistence kundePersistence) {
        this.kundePersistence = kundePersistence;
    }
    
    public int freieKundennummer(){
        return kundePersistence.findFreieKundennummer();
    }
    
}
