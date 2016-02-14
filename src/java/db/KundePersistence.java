/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import entities.Kunde;
import entities.Schrank;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Katha
 */
@Stateless
public class KundePersistence extends AbstractPersistence<Kunde> {
    
    @Inject
    private SchrankPersistence schrankPersistence;
    
    @Inject
    private EntityManager manager;

    @Override
    public EntityManager getEntityManager() {
        return manager;
    }

    public void setManager(EntityManager manager) {
        this.manager = manager;
    }
    
    public KundePersistence() {
        super(Kunde.class);
    }
    
    public void persistSchrank(Schrank schrank){
        manager.persist(schrank);
    }
    
    public List<Schrank> findAlleSchraenke(){
        return (ArrayList<Schrank>) manager.createQuery("SELECT s FROM Schrank s", Schrank.class).getResultList();
    }
    /*
    public Object merge(Object object){
        return manager.merge(object);
    }*/
    
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
        Schrank auscheckSchrank = schrankPersistence.findSchrankByKundenId(id);
        manager.find(Schrank.class, auscheckSchrank).setKunde(null);
        manager.merge(findKundeById(id));
    }
    
    /*public List<Kunde> findAlleKunden(){
        return manager.createQuery("SELECT k FROM Kunde k", Kunde.class).getResultList();
    }*/
    
    public void schrankZuweisen(long id, int schrank){     
        schrankPersistence.findSchrankById(schrank).setKunde(findKundeById(id));
        manager.merge(findKundeById(id));
    }
    
    public Kunde findKundeByKundennummer(int kundennummer){
        List<Kunde> listKunde = findAll();
        if(listKunde.size()==0){
            return null;
        }else{
            for(int i = 0; i < listKunde.size(); i++){
                if(listKunde.get(i).getKundennummer()==kundennummer){
                    return listKunde.get(i);
                }
            }
            return null;
        }
        
        /*if((Kunde) manager.createQuery("SELECT k FROM Kunde k WHERE k.kundennummer := kundennummer").getSingleResult() !=null){
            return (Kunde) manager.createQuery("SELECT k FROM Kunde k WHERE k.kundennummer := kundennummer").getSingleResult();
        }
        else{
            return null;
        }*/
        
    }
    
    
    
    public int findFreieKundennummer(){
        int freieNummer = -1;
        boolean frei;
        List<Kunde> alleKunden = this.findAll();
        for(int i = 10000; i < 99999; i++){
            frei = true;
            for(int j = 0; j < alleKunden.size(); j++){
                if((alleKunden.get(j).getKundennummer() == i)){
                    frei = false;
                }
            }
            if(frei){
                freieNummer = i;
                break;
            }
        }
        return freieNummer;
    }

}
