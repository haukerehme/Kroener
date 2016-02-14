/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import entities.Kunde;
import entities.Schrank;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 *
 * @author Katha
 */
@Stateless
public class Persistence {
    
    @Inject
    private EntityManager manager;

    public EntityManager getManager() {
        return manager;
    }

    public void setManager(EntityManager manager) {
        this.manager = manager;
    }
    
    public Persistence() {
    }
    
    public void persist(Object object){
        manager.persist(object);
    }
    
    public Object merge(Object object){
        return manager.merge(object);
    }
    
    public List<Kunde> findEingecheckteKunden(){
        return manager.createQuery("SELECT k FROM Kunde k WHERE k.eingecheckt = true").getResultList();
    }
    
    public Kunde findKundeById(long id){
        Kunde k = manager.find(Kunde.class, id);
        if(k==null){
            return null;
        }
        return k;
    }
    
    public void einchecken(long id){
        manager.find(Kunde.class, id).setEingecheckt(true);
        manager.merge(findKundeById(id));
    }
    
    public void auschecken(long id){
        manager.find(Kunde.class, id).setEingecheckt(false);
        Schrank auscheckSchrank = findSchrankByKundenId(id);
        manager.find(Schrank.class, auscheckSchrank).setKunde(null);
        manager.merge(findKundeById(id));
    }
    
    public List<Kunde> findAlleKunden(){
        return manager.createQuery("SELECT k FROM Kunde k", Kunde.class).getResultList();

    }
    
    public void schrankZuweisen(long id, int schrank){
        manager.find(Schrank.class, schrank).setKunde(manager.find(Kunde.class, id));
        manager.merge(findKundeById(id));
    }
    
    public List<Schrank> findAlleSchraenke(){
        return manager.createQuery("SELECT s FROM Schrank s", Schrank.class).getResultList();

    }
    
    public Schrank findSchrankByKundenId(long id){
        return (Schrank) manager.createQuery("SELECT s FROM Schrank s WHERE s.kunde.id := id").getSingleResult();
    }
    
    public void initSchranknummern(){
        
    }
    
    
}
