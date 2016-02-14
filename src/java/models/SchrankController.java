 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import db.KundePersistence;
import db.SchrankPersistence;
import entities.Kunde;
import entities.Schrank;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

/**
 *
 * @author Katha
 */
@Dependent
public class SchrankController implements Serializable{
    
    private List<Schrank> schrankList;
    
    @Inject
    private KundePersistence kundePersistence;
    
    @Inject
    private SchrankPersistence schrankPersistence;
    
    
    private int letzterVergebenerSchrank = -1;
    private int anzahlSchraenke = 100;
    
    
    SchrankController() {
        //schrankList = (ArrayList<Schrank>) persistence.findAlleSchraenke();

    }
    
    
    
    /**
     * Initialisiert die Schränke
     */
    public void initSchrankList(){
        
        if(schrankPersistence.findAll().size() == 0){
            schrankList = new ArrayList();
            int schranknummer = 10000;
            for(int i = 0; i < anzahlSchraenke; i++){
                    this.schrankList.add(new Schrank(i+1,i+1,null));
                    schrankList.get(i).setSchranknummer(schranknummer + i);
                    kundePersistence.persistSchrank(this.schrankList.get(i));
                }
            }
    }
    
    /**
     *
     * @return Gibt die Nummer eines verfügbaren Schrankes zurück
     */
    public int schrankVerfuegbar(){
        int verfuegbarerSchrank = -1;
        if(schrankList==null){
            schrankList = schrankPersistence.findAll();
        }
        
       // schrankList = (ArrayList<Schrank>) kundePersistence.findAlleSchraenke();
        for(int i = 0; i < anzahlSchraenke; i++){
            if(schrankList.get(i).getKunde() == null){
                int schrankDiff = letzterVergebenerSchrank - schrankList.get(i).getSchranknummer();
                if(!(schrankDiff == 1 || schrankDiff == -1)){
                    return schrankList.get(i).getSchranknummer();
                }
                else{
                    verfuegbarerSchrank = schrankList.get(i).getSchranknummer();
                }
            }
        }
        return verfuegbarerSchrank;
    }
    
    /**
     *
     * @param idKunde
     * @return
     */
    public int einchecken(int kundennummer){
        int leererSchrank = schrankVerfuegbar();
        if(leererSchrank != -1){
            if(schrankPersistence.findAll().size() == 0){
                for(int i =0; i < schrankList.size(); i++){
                    schrankPersistence.persist(schrankList.get(i));
                }
            }
            schrankList.get(leererSchrank-1).setKunde(kundePersistence.findKundeByKundennummer(kundennummer));
            letzterVergebenerSchrank = leererSchrank;
        }
        kundePersistence.schrankZuweisen(kundePersistence.findKundeByKundennummer(kundennummer).getId(), leererSchrank);
        return leererSchrank;
    }
    
    
    
    /**
     *
     * @param id
     */
    public void auschecken(long id){
        Schrank auscheckSchrank = findSchrankByKundenId(id);
        if(!(auscheckSchrank == null)){
            schrankList.get(auscheckSchrank.getId().intValue()).setKunde(null);
        }
    }
    
    /**
     *
     * @param id
     * @return
     */
    public Schrank findSchrankByKundenId(long id){
        for(Schrank s : schrankList){
            if(s.getKunde().getId() == id){
                return s;
            }
        }
        return null;
    }

    public List<Schrank> getSchrankList() {
        return schrankList;
    }

    public void setSchrankList(ArrayList<Schrank> schrankList) {
        this.schrankList = schrankList;
    }

    public KundePersistence getKundePersistence() {
        return kundePersistence;
    }

    public void setKundePersistence(KundePersistence kundePersistence) {
        this.kundePersistence = kundePersistence;
    }

    public SchrankPersistence getSchrankPersistence() {
        return schrankPersistence;
    }

    public void setSchrankPersistence(SchrankPersistence schrankPersistence) {
        this.schrankPersistence = schrankPersistence;
    }


    public int getLetzterVergebenerSchrank() {
        return letzterVergebenerSchrank;
    }

    public void setLetzterVergebenerSchrank(int letzterVergebenerSchrank) {
        this.letzterVergebenerSchrank = letzterVergebenerSchrank;
    }

    public int getAnzahlSchraenke() {
        return anzahlSchraenke;
    }

    public void setAnzahlSchraenke(int anzahlSchraenke) {
        this.anzahlSchraenke = anzahlSchraenke;
    }
    
    
}
