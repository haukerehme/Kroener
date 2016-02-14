/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import db.Persistence;
import entities.Kunde;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
    @Inject
    private Persistence db;

    public void neuerKunde(Kunde kunde){
        db.persist(kunde);
    }
    
    public KundeModel(){
    }
    
    public List<Kunde> eingecheckteKunden(){
        List<Kunde> ergebnis = new ArrayList<>();
        for(Kunde k:this.db.findEingecheckteKunden()){
            ergebnis.add(k);
        }
        return ergebnis;
    }
    
    
    public String checkIn(long id){
        Kunde kunde = this.db.findKundeById(id);
        if(kunde == null){
            return "Es existiert kein Kunde mit der eingegebenen Kundennummer (" + id + ")";
        }
        else if(kunde.isEingecheckt()){
            return "Kunde ist bereits eingecheckt";
        }
        else{
            SchrankController schrankController = SchrankController.getInstance();
            int schranknummer = schrankController.einchecken(id);
            if(schranknummer != -1){
                this.db.einchecken(id);
                return "Kunde erfolgreich eingecheckt. Schranknummer: " + schranknummer;
            }else{
                return "Kein Schrank mehr verfügbar";
            }
        }
    }
    
    public void kundenBearbeiten(Kunde kunde){
        db.merge(kunde);
    }
    
    public String kundenSuchen(long id){
        if(db.findAlleKunden().isEmpty()){
            return "Es existiert noch kein Kunde.";
        }
        else if(db.findKundeById(id) == null){
            return "Es existiert kein Kunde mit der eingegebenen Kundennummer (" + id + ")";
        }
        else{
            return db.findKundeById(id).toString();
        }
    }
    
    public String checkOut(long id){
        if(db.findAlleKunden().isEmpty()){
            return "Es existiert noch kein Kunde.";
        }
        else if(db.findKundeById(id) == null){
            return "Es existiert kein Kunde mit der eingegebenen Kundennummer (" + id + ")";
        }
        else if(!db.findKundeById(id).isEingecheckt()){
            return "Kunde ist nicht eingecheckt.";
        }
        else{
            db.auschecken(id);
            return "Kunde erfolgreich ausgecheckt";
        }
    }

    public Persistence getDb() {
        return db;
    }

    public void setDb(Persistence db) {
        this.db = db;
    }
    
}
