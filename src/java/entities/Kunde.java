/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.*;

/**
 *
 * @author Katha
 */
@Entity
public class Kunde implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    //@Min(10000)
    private Long id;
 
    private int kundennummer;
    
    private String vorname;
    
    private String nachname;
    
    private String strasse;
    
    private String ort;
    
    private String hausnummer;
    
    private String postleitzahl;
    
    private String bemerkungen;
    
    private String vertragsart;
    
    private int vertragslaufzeit;
    
    private String telefonnummer;
    
    private boolean eingecheckt;
    
    public Kunde(){
    }

    public Kunde(int kundennummer, String vorname, String nachname, String strasse, String ort, String hausnummer, String postleitzahl, String bemerkungen, String vertragsart, int vertragslaufzeit, String telefonnummer, boolean eingecheckt) {
        this.kundennummer = kundennummer;
        this.vorname = vorname;
        this.nachname = nachname;
        this.strasse = strasse;
        this.ort = ort;
        this.hausnummer = hausnummer;
        this.postleitzahl = postleitzahl;
        this.bemerkungen = bemerkungen;
        this.vertragsart = vertragsart;
        this.vertragslaufzeit = vertragslaufzeit;
        this.telefonnummer = telefonnummer;
        this.eingecheckt = eingecheckt;
    }

   

    

    public int getKundennummer() {
        return kundennummer;
    }

    public void setKundennummer(int kundennummer) {
        this.kundennummer = kundennummer;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Kunde)) {
            return false;
        }
        Kunde other = (Kunde) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Kunde: " + kundennummer + "\n"
                + vorname + " " + nachname + "\n"
                + strasse + " " + hausnummer + ", " + postleitzahl +" "+ ort + "\n"
                + telefonnummer + "\n"
                + vertragsart + ", " + vertragslaufzeit + "\n"
                + bemerkungen;
    }

    public boolean isEingecheckt() {
        return eingecheckt;
    }

    public void setEingecheckt(boolean eingecheckt) {
        this.eingecheckt = eingecheckt;
    }
    
}
