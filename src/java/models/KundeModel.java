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

    public void neuerKunde(Kunde kunde) throws Exception {
        try {
            db.persist(kunde);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            if (db.getManager() != null) {
                db.getManager().close();
            }
        }
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
        if(this.db.findKundeById(id).isEingecheckt()){
            return "Kunde ist bereits eingecheckt";
        }
        if(this.db.findKundeById(id) == null){
            return "Es existiert kein Kunde mit der eingegebenen Kundennummer (" + id + ")";
        }
        else{
            this.db.einchecken(id);
            return "Kunde erfolgreich eingecheckt";
        }
    }
    
    
    public void kundenBearbeiten(Kunde kunde){
        db.merge(kunde);
    }
}
