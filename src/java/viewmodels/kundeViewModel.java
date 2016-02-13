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
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import models.KundeModel;

/**
 *
 * @author Katha
 */
@Named(value="kvm")
@RequestScoped
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
    @EJB
    private Persistence persistence;
    
    public kundeViewModel(){
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
    
    public String checkIn(){
        if(persistence.findAlleKunden().isEmpty()){
            this.setEincheckNachricht("Es existiert noch kein Kunde.");
            System.out.println("Es existiert noch kein Kunde.");
        }
        else if(this.persistence.findKundeById(id) == null){
            this.setEincheckNachricht("Es existiert kein Kunde mit der eingegebenen Kundennummer (" + id + ")");
            System.out.println("Es existiert kein Kunde mit der eingegebenen Kundennummer (" + id + ")");
        }
        else if(this.persistence.findKundeById(id).isEingecheckt()){
            this.setEincheckNachricht("Kunde ist bereits eingecheckt.");
            System.out.println("Kunde ist bereits eingecheckt.");
        }
        else{
            this.persistence.einchecken(id);
            this.setEincheckNachricht("Kunde erfolgreich eingecheckt");
            System.out.println("Kunde erfolgreich eingecheckt");
        }
        return konstanten.Navigation.EINGECHECKT;
    }
    
    public String checkOut(){
        if(persistence.findAlleKunden().isEmpty()){
            this.setEincheckNachricht("Es existiert noch kein Kunde.");
            System.out.println("Es existiert noch kein Kunde.");
        }
        else if(this.persistence.findKundeById(id) == null){
            this.setEincheckNachricht("Es existiert kein Kunde mit der eingegebenen Kundennummer (" + id + ")");
            System.out.println("Es existiert kein Kunde mit der eingegebenen Kundennummer (" + id + ")");
        }
        else if(!this.persistence.findKundeById(id).isEingecheckt()){
            this.setEincheckNachricht("Kunde ist nicht eingecheckt.");
            System.out.println("Kunde ist nicht eingecheckt.");
        }
        else{
            this.persistence.auschecken(id);
            this.setEincheckNachricht("Kunde erfolgreich ausgecheckt");
            System.out.println("Kunde erfolgreich ausgecheckt");
        }
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
        return konstanten.Navigation.STARTSEITE;
    }
    
    public String kundenSuchen(){
        if(persistence.findAlleKunden().isEmpty()){
            this.setSuchErgebnis("Es existiert noch kein Kunde.");
            System.out.println("Es existiert noch kein Kunde.");
        }
        else if(this.persistence.findKundeById(id) == null){
            this.setSuchErgebnis("Es existiert kein Kunde mit der eingegebenen Kundennummer (" + id + ")");
            System.out.println("Es existiert kein Kunde mit der eingegebenen Kundennummer (" + id + ")");
        }
        else{
            this.setSuchErgebnis(this.persistence.findKundeById(id).toString());
            System.out.println(this.persistence.findKundeById(id).toString());
        }
        return konstanten.Navigation.SUCHERGEBNIS;
    }
    
    public String zurBearbeitung(){
        return konstanten.Navigation.KUNDENBEARBEITEN;
    }
    
    public String kundenBearbeiten(){
        Kunde k = persistence.findKundeById(id);
        k.setVorname(vorname);
        k.setNachname(nachname);
        k.setStrasse(strasse);
        k.setOrt(ort);
        k.setHausnummer(hausnummer);
        k.setPostleitzahl(postleitzahl);
        k.setBemerkungen(bemerkungen);
        k.setEingecheckt(false);
        k.setVertragsart(vertragsart);
        k.setVertragslaufzeit(vertragslaufzeit);
        persistence.merge(k);
        return konstanten.Navigation.EINGECHECKT;
    }
    
    public String neuerKunde(){
        
        Kunde k = new Kunde();
        
       /* k.setVorname("hauke");
        k.setNachname("re");
        k.setStrasse("er");
        k.setOrt("bbb");
        k.setHausnummer(2);
        k.setPostleitzahl(2343);
        k.setBemerkungen("sd");
        k.setEingecheckt(false);
        k.setVertragsart("sdfds");
        k.setVertragslaufzeit(12);*/
        
        k.setVorname(vorname);
        k.setNachname(nachname);
        k.setStrasse(strasse);
        k.setOrt(ort);
        k.setHausnummer(hausnummer);
        k.setPostleitzahl(postleitzahl);
        k.setBemerkungen(bemerkungen);
        k.setEingecheckt(false);
        k.setVertragsart(vertragsart);
        k.setVertragslaufzeit(vertragslaufzeit);
        
        persistence.persist(k);
        
        
        
        
        
        /*try{
        kundemodel.neuerKunde(k);
        } catch(Exception e){
            
            System.out.println("knn Kunden nicht erstellen"+ e.getMessage());
        }*/
        zuruecksetzen();
        return konstanten.Navigation.KUNDEANGELEGT;
    }
    
    public void zuruecksetzen(){
        this.setVorname("");
        this.setNachname("");
        this.setStrasse("");
        this.setOrt("");
        this.setHausnummer(0);
        this.setPostleitzahl(0);
        this.setBemerkungen("");
        this.setEingecheckt(false);
        this.setVertragsart("");
        this.setVertragslaufzeit(0);
        this.setEincheckNachricht("");
    }
    
   
}

