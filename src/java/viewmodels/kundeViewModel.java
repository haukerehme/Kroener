/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewmodels;


import db.Persistence;
import entities.Kunde;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import models.KundeModel;
import models.SchrankController;

/**
 *
 * @author Katha
 */
@Named(value="kvm")
@SessionScoped
public class kundeViewModel implements Serializable{
    private long id;
    private String vorname;
    private String nachname;
    private String strasse;
    private String ort;
    private int hausnummer;
    private int postleitzahl;
    private String bemerkungen;
    private String vertragsart;
    private int vertragslaufzeit;
    private long telefonnummer;
    private boolean eingecheckt;
    private String eincheckNachricht;
    private String suchErgebnis;
    @Inject
    private KundeModel kundemodel;
    
    public kundeViewModel(){
    }
    
    public String kundenSuchen(long id){
        this.id = id;
        this.suchErgebnis = kundemodel.kundenSuchen(id);
        zuruecksetzen();
        return konstanten.Navigation.SUCHERGEBNIS;
    }
    
    public String zurBearbeitung(long id){
        setThisValues(kundemodel.getDb().findKundeById(id));
        return konstanten.Navigation.KUNDENBEARBEITEN;
    }
    
    public String kundenBearbeiten(){
        Kunde k = kundemodel.getDb().findKundeById(id);
        setKundeValues(k);
        kundemodel.kundenBearbeiten(k);
        zuruecksetzen();
        return konstanten.Navigation.EINGECHECKT;
    }
    
    public String neuerKunde(){
        Kunde k = new Kunde(vorname,nachname,strasse,ort,hausnummer,postleitzahl,bemerkungen,vertragsart,vertragslaufzeit,telefonnummer,false);
        kundemodel.neuerKunde(k);
        zuruecksetzen();
        return konstanten.Navigation.KUNDEANGELEGT;
    }
    
    
    public void setThisValues(Kunde k){
        id = k.getId();
        vorname = k.getVorname();
        nachname = k.getNachname();
        strasse = k.getStrasse();
        ort = k.getOrt();
        hausnummer = k.getHausnummer();
        postleitzahl = k.getPostleitzahl();
        telefonnummer = k.getTelefonnummer();
        bemerkungen = k.getBemerkungen();
        eingecheckt = k.isEingecheckt();
        vertragsart = k.getVertragsart();
        vertragslaufzeit = k.getVertragslaufzeit();
    }
    
    public void setKundeValues(Kunde k){
        k.setVorname(vorname);
        k.setNachname(nachname);
        k.setStrasse(strasse);
        k.setOrt(ort);
        k.setHausnummer(hausnummer);
        k.setPostleitzahl(postleitzahl);
        k.setTelefonnummer(telefonnummer);
        k.setBemerkungen(bemerkungen);
        k.setEingecheckt(false);
        k.setVertragsart(vertragsart);
        k.setVertragslaufzeit(vertragslaufzeit);
    }
    
    public void zuruecksetzen(){
        vorname = "";
        nachname = "";
        strasse = "";
        ort = "";
        hausnummer = -1;
        postleitzahl = -1;
        telefonnummer = -1;
        bemerkungen = "";
        eingecheckt = false;
        vertragsart = "";
        vertragslaufzeit = -1;
    }
   
    public KundeModel getKundemodel() {
        return kundemodel;
    }

    public void setKundemodel(KundeModel kundemodel) {
        this.kundemodel = kundemodel;
    }

    public String getSuchErgebnis() {
        return suchErgebnis;
    }

    public void setSuchErgebnis(String suchErgebnis) {
        this.suchErgebnis = suchErgebnis;
    }

    public String getEincheckNachricht() {
        return eincheckNachricht;
    }

    public void setEincheckNachricht(String eincheckNachricht) {
        this.eincheckNachricht = eincheckNachricht;
    }
    
    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public String getStrasse() {
        return strasse;
    }

    public void setStrasse(String strasse) {
        this.strasse = strasse;
    }

    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    public int getHausnummer() {
        return hausnummer;
    }

    public void setHausnummer(int hausnummer) {
        this.hausnummer = hausnummer;
    }

    public int getPostleitzahl() {
        return postleitzahl;
    }

    public void setPostleitzahl(int postleitzahl) {
        this.postleitzahl = postleitzahl;
    }

    public String getBemerkungen() {
        return bemerkungen;
    }

    public void setBemerkungen(String bemerkungen) {
        this.bemerkungen = bemerkungen;
    }

    public String getVertragsart() {
        return vertragsart;
    }

    public void setVertragsart(String vertragsart) {
        this.vertragsart = vertragsart;
    }

    public int getVertragslaufzeit() {
        return vertragslaufzeit;
    }

    public void setVertragslaufzeit(int vertragslaufzeit) {
        this.vertragslaufzeit = vertragslaufzeit;
    }

    public long getTelefonnummer() {
        return telefonnummer;
    }

    public void setTelefonnummer(long telefonnummer) {
        this.telefonnummer = telefonnummer;
    }

    public boolean isEingecheckt() {
        return eingecheckt;
    }

    public void setEingecheckt(boolean eingecheckt) {
        this.eingecheckt = eingecheckt;
    }
    
    public List<Kunde> eingecheckteKunden(){
        return this.kundemodel.eingecheckteKunden();
    }
    
    
    public long getId(){
        return id;
    }
    
    public void setId(long id){
        this.id = id;
    }
    
    
    public String zumCheckin(){
        return konstanten.Navigation.CHECKIN;
    }
    
    public String checkIn(long id){
        
        //if(SchrankController.getInstance().einchecken(id) != -1){
            this.eincheckNachricht = kundemodel.checkIn(id);
            zuruecksetzen();
            return konstanten.Navigation.EINGECHECKT;
        /*}else{
            
            return konstanten.Navigation.EINGECHECKT;
        }*/
        
        
    }
    
    public String checkOut(){
        this.eincheckNachricht = kundemodel.checkOut(id);
        zuruecksetzen();
        return konstanten.Navigation.EINGECHECKT;
    }
    
    public String zumCheckout(){
        return konstanten.Navigation.CHECKOUT;
    }
    
    public String zumAnlegen(){
        return konstanten.Navigation.NEUERKUNDE;
    }
    
    public String zurKundensuche(){
        return konstanten.Navigation.KUNDENSUCHEN;
    }
    
    public String zurStartseite(){
        zuruecksetzen();
        return konstanten.Navigation.STARTSEITE;
    }
}

