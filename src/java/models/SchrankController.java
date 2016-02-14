/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import db.Persistence;
import entities.Schrank;
import java.util.ArrayList;
import javax.ejb.EJB;

/**
 *
 * @author Katha
 */

public class SchrankController{
    
    private ArrayList<Schrank> schrankList;
    
    @EJB
    private Persistence persistence;
    
    private int letzterVergebenerSchrank;
    private int anzahlSchraenke;
    
    
    private SchrankController() {
        /*schrankList = (ArrayList<Schrank>) persistence.findAlleSchraenke();
        if(schrankList.isEmpty()){
            for(int i = 0; i < 50; i++){
                this.schrankList.add(new Schrank(null));
            }
        }*/
    }
    
    public void initSchrankList(){
        int schranknummer = 10000;
        for(int i = 0; i < anzahlSchraenke; i++){
            schrankList.get(i).setSchranknummer(schranknummer + i);
        }
    }
    
    public int schrankVerfuegbar(){
        int verfuegbarerSchrank = -1;
        for(int i = 0; i < 50; i++){
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
    
    public int einchecken(long id){
        int leererSchrank = schrankVerfuegbar();
        if(leererSchrank != -1){
            schrankList.get(leererSchrank).setKunde(persistence.findKundeById(id));
            letzterVergebenerSchrank = leererSchrank;
        }
        persistence.schrankZuweisen(id, leererSchrank);
        return leererSchrank;
    }
    
    public static SchrankController getInstance() {
        return SchrankControllerHolder.INSTANCE;
    }
    
    public void auschecken(long id){
        Schrank auscheckSchrank = findSchrankByKundenId(id);
        if(!(auscheckSchrank == null)){
            schrankList.get(auscheckSchrank.getId().intValue()).setKunde(null);
        }
    }
    
    public Schrank findSchrankByKundenId(long id){
        for(Schrank s : schrankList){
            if(s.getKunde().getId() == id){
                return s;
            }
        }
        return null;
    }
    
    private static class SchrankControllerHolder {

        private static final SchrankController INSTANCE = new SchrankController();
    }
    
}
