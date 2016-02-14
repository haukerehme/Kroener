/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewmodels;


import db.KundePersistence;
import entities.Kunde;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
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
    private int kundennummer;
    
    
    @Size(min=2, max=30, message="Der Vorname muss 2-30 Zeichen haben")
    @Pattern(regexp="^[A-Z][\\s-a-zA-Zéüäöóß]+$", message="Der Vorname muss einen vorangestellten großen Buchstaben haben. Außerdem sind nur die Zeichen a-z,A-Z,é,ü,ä,ö,ó,- und Leerzeichen erlaubt")
    private String vorname;
    
    
    @Size(min=3, max=50, message="Der Nachname muss 3-50 Zeichen haben")
    @Pattern(regexp="^[A-Z][\\s-a-zA-Zéüäöóß]+$", message="Der Nachname muss einen vorangestellten großen Buchstaben haben. Außerdem sind nur die Zeichen a-z,A-Z,é,ü,ä,ö,ó,- und Leerzeichen erlaubt")
    private String nachname;
    
    
    @Size(min=3, max=50, message="Die Straße muss 3-50 Zeichen haben")
    @Pattern(regexp="^[A-Z][\\s-a-zA-Zéüäöóß]+$", message="Die Straße muss einen vorangestellten großen Buchstaben haben. Außerdem sind nur die Zeichen a-z,A-Z,é,ü,ä,ö,ó,- und Leerzeichen erlaubt")
    private String strasse;
    
    @NotNull
    @Size(min=2, max=50, message="Der Ort muss 2-50 Zeichen haben")
    @Pattern(regexp="^[A-Z][\\s-a-zA-Zéüäöóß]+$", message="Der Ort muss einen vorangestellten großen Buchstaben haben. Außerdem sind nur die Zeichen a-z,A-Z,é,ü,ä,ö,ó,- und Leerzeichen erlaubt")
    private String ort;
    
    
    @Size(min=1, max=16, message="Die Hausnummer muss 1-16 Zeichen haben")
    private String hausnummer;

    @Size(min=4, max=7, message="Die Postleitzahl muss 4-7 Zeichen haben")
    private String postleitzahl;
    
    private String bemerkungen;
    
    @Size(min=1, max=30, message="Die Vertragsart muss 1-30 Zeichen haben")
    private String vertragsart;
    
    @Min(value = 1, message="Vertragslaufzeit muss mindestens 1 sein.")
    @Max(value = 36, message="Vertragslaufzeit darf höchstens 36 sein.")
    private int vertragslaufzeit;
    
    @Size(min=4, max=30, message="Die Telefonnummer muss aus 4-30 Zeichen bestehen")
    @Pattern(regexp="^[0-9]+$",message="Für die Telefonnummer bitte nur Zahlen verwenden")
    private String telefonnummer;
    
    
    private boolean eingecheckt;
    
    
    private String eincheckNachricht;
    
    
    private String suchErgebnis;
    
    
    @Inject
    private KundeModel kundemodel;
    
    public kundeViewModel(){
    }
    
    public String kundenSuchErgebnis(int kundennummer){
        this.kundennummer = kundennummer;
        if(kundemodel.kundenSuchen(kundennummer)!=null){
            setThisValues(kundemodel.kundenSuchen(kundennummer));
        }
        
        this.suchErgebnis = kundemodel.kundenSuchErgebnis(kundennummer);
        //zuruecksetzen();
        return konstanten.Navigation.SUCHERGEBNIS;
    }
    
    public String zurBearbeitung(long id){
        setThisValues(kundemodel.getKundePersistence().findKundeById(id));
        return konstanten.Navigation.KUNDENBEARBEITEN;
    }
    
    public String kundenBearbeiten(long id){
        Kunde k = kundemodel.getKundePersistence().findKundeById(id);
        setKundeValues(k);
        kundemodel.kundenBearbeiten(k);
        zuruecksetzen();
        return konstanten.Navigation.STARTSEITE;
    }
    
    public String loeschen(long id){
        kundemodel.getKundePersistence().remove(kundemodel.getKundePersistence().findKundeById(id));
        return konstanten.Navigation.STARTSEITE;
    }
    
    public String neuerKunde(){ 
        int freieKundennummer = kundemodel.freieKundennummer();
        Kunde k = new Kunde(freieKundennummer,vorname,nachname,strasse,ort,hausnummer,postleitzahl,bemerkungen,vertragsart,vertragslaufzeit,telefonnummer,false);
        kundemodel.neuerKunde(k);
        zuruecksetzen();
        return konstanten.Navigation.KUNDEANGELEGT;
    }
    
    
    public void setThisValues(Kunde k){
        id = k.getId();
        kundennummer = k.getKundennummer();
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
        k.setKundennummer(kundennummer);
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
        hausnummer = "";
        postleitzahl = "";
        telefonnummer = "";
        bemerkungen = "";
        eingecheckt = false;
        vertragsart = "";
        vertragslaufzeit = -1;
    }

    public int getKundennummer() {
        return kundennummer;
    }

    public void setKundennummer(int kundennummer) {
        this.kundennummer = kundennummer;
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

    public String getHausnummer() {
        return hausnummer;
    }

    public void setHausnummer(String hausnummer) {
        this.hausnummer = hausnummer;
    }

    public String getPostleitzahl() {
        return postleitzahl;
    }

    public void setPostleitzahl(String postleitzahl) {
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

    public String getTelefonnummer() {
        return telefonnummer;
    }

    public void setTelefonnummer(String telefonnummer) {
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
        kundemodel.initSchrankController();
        return konstanten.Navigation.CHECKIN;
    }
    
    public String checkIn(int kundennummer){
        
        
        this.eincheckNachricht = kundemodel.checkIn(kundennummer);
        return konstanten.Navigation.EINGECHECKT;
        
        
        
    }
    
    public String checkOut(long id){
        this.eincheckNachricht = kundemodel.checkOut(id);
        zuruecksetzen();
        return konstanten.Navigation.EINGECHECKT;
    }
    
    public String zumCheckout(long id){
        checkOut(id);
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

