/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import entities.Kunde;
import entities.Schrank;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 *
 * @author katha
 */
public class SchrankPersistence extends AbstractPersistence<Schrank> {
    
    @Inject
    private EntityManager manager;

    @Override
    public EntityManager getEntityManager() {
        return manager;
    }
    
    public SchrankPersistence(){
        super(Schrank.class);
    }
    
    
    
    public Schrank findSchrankById(long id){
        Schrank s = manager.find(Schrank.class, id);
        if(s==null){
            return null;
        }
        return s;
    }
    
    public Schrank findSchrankByKundenId(long id){
        return (Schrank) manager.createQuery("SELECT s FROM Schrank s WHERE s.kunde.id := id").getSingleResult();
    }
}
