package com.mypms.customers.model;

import javafx.beans.property.*;

import java.time.LocalDateTime;

/**
 * Model κλάση για τον Πελάτη - JavaFX Edition με Properties
 * Χρησιμοποιεί JavaFX Properties για data binding
 */
public class Pelatis {

    // Properties για JavaFX binding
    private final StringProperty kodikosPelati = new SimpleStringProperty();
    private final StringProperty kategoria = new SimpleStringProperty();
    private final StringProperty afmNomimuEkprosopu = new SimpleStringProperty();
    private final StringProperty eponymiaEtairias = new SimpleStringProperty();
    private final StringProperty diakritikosTitlos = new SimpleStringProperty();
    private final StringProperty nomikiMorfi = new SimpleStringProperty();
    private final StringProperty epaggelmaAntikimeno = new SimpleStringProperty();
    private final StringProperty afmEtairias = new SimpleStringProperty();
    private final StringProperty douEtairias = new SimpleStringProperty();
    private final StringProperty nomimosEkprospos = new SimpleStringProperty();

    // Επικοινωνία
    private final StringProperty email = new SimpleStringProperty();
    private final StringProperty tilefonoStathero = new SimpleStringProperty();
    private final StringProperty tilefonoKinito = new SimpleStringProperty();

    // Διεύθυνση
    private final StringProperty poli = new SimpleStringProperty();
    private final StringProperty perioxi = new SimpleStringProperty();
    private final StringProperty odos = new SimpleStringProperty();
    private final StringProperty arithmosDiefthinsis = new SimpleStringProperty();
    private final StringProperty tk = new SimpleStringProperty();

    // Timestamps
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Default constructor
    public Pelatis() {}

    // Constructor με όλα τα πεδία
    public Pelatis(String kodikosPelati, String kategoria, String eponymiaEtairias) {
        setKodikosPelati(kodikosPelati);
        setKategoria(kategoria);
        setEponymiaEtairias(eponymiaEtairias);
    }

    // Getters and Setters με JavaFX Property support

    // Κωδικός Πελάτη
    public String getKodikosPelati() { return kodikosPelati.get(); }
    public void setKodikosPelati(String value) { kodikosPelati.set(value); }
    public StringProperty kodikosPelatiProperty() { return kodikosPelati; }

    // Κατηγορία
    public String getKategoria() { return kategoria.get(); }
    public void setKategoria(String value) { kategoria.set(value); }
    public StringProperty kategoriaProperty() { return kategoria; }

    // ΑΦΜ Νόμιμου Εκπροσώπου
    public String getAfmNomimuEkprosopu() { return afmNomimuEkprosopu.get(); }
    public void setAfmNomimuEkprosopu(String value) { afmNomimuEkprosopu.set(value); }
    public StringProperty afmNomimuEkprosopuProperty() { return afmNomimuEkprosopu; }

    // Επωνυμία Εταιρίας
    public String getEponymiaEtairias() { return eponymiaEtairias.get(); }
    public void setEponymiaEtairias(String value) { eponymiaEtairias.set(value); }
    public StringProperty eponymiaEtairiasProperty() { return eponymiaEtairias; }

    // Διακριτικός Τίτλος
    public String getDiakritikosTitlos() { return diakritikosTitlos.get(); }
    public void setDiakritikosTitlos(String value) { diakritikosTitlos.set(value); }
    public StringProperty diakritikosTitlosProperty() { return diakritikosTitlos; }

    // Νομική Μορφή
    public String getNomikiMorfi() { return nomikiMorfi.get(); }
    public void setNomikiMorfi(String value) { nomikiMorfi.set(value); }
    public StringProperty nomikiMorfiProperty() { return nomikiMorfi; }

    // Επάγγελμα/Αντικείμενο
    public String getEpaggelmaAntikimeno() { return epaggelmaAntikimeno.get(); }
    public void setEpaggelmaAntikimeno(String value) { epaggelmaAntikimeno.set(value); }
    public StringProperty epaggelmaAntikimenoProperty() { return epaggelmaAntikimeno; }

    // ΑΦΜ Εταιρίας
    public String getAfmEtairias() { return afmEtairias.get(); }
    public void setAfmEtairias(String value) { afmEtairias.set(value); }
    public StringProperty afmEtairiasProperty() { return afmEtairias; }

    // ΔΟΥ Εταιρίας
    public String getDouEtairias() { return douEtairias.get(); }
    public void setDouEtairias(String value) { douEtairias.set(value); }
    public StringProperty douEtairiasProperty() { return douEtairias; }

    // Νόμιμος Εκπρόσωπος
    public String getNomimosEkprospos() { return nomimosEkprospos.get(); }
    public void setNomimosEkprospos(String value) { nomimosEkprospos.set(value); }
    public StringProperty nomimosEkprosposProperty() { return nomimosEkprospos; }

    // Email
    public String getEmail() { return email.get(); }
    public void setEmail(String value) { email.set(value); }
    public StringProperty emailProperty() { return email; }

    // Τηλέφωνο Σταθερό
    public String getTilefonoStathero() { return tilefonoStathero.get(); }
    public void setTilefonoStathero(String value) { tilefonoStathero.set(value); }
    public StringProperty tilefonoStatheroProperty() { return tilefonoStathero; }

    // Τηλέφωνο Κινητό
    public String getTilefonoKinito() { return tilefonoKinito.get(); }
    public void setTilefonoKinito(String value) { tilefonoKinito.set(value); }
    public StringProperty tilefonoKinitoProperty() { return tilefonoKinito; }

    // Πόλη
    public String getPoli() { return poli.get(); }
    public void setPoli(String value) { poli.set(value); }
    public StringProperty poliProperty() { return poli; }

    // Περιοχή
    public String getPerioxi() { return perioxi.get(); }
    public void setPerioxi(String value) { perioxi.set(value); }
    public StringProperty perioxiProperty() { return perioxi; }

    // Οδός
    public String getOdos() { return odos.get(); }
    public void setOdos(String value) { odos.set(value); }
    public StringProperty odosProperty() { return odos; }

    // Αριθμός Διεύθυνσης
    public String getArithmosDiefthinsis() { return arithmosDiefthinsis.get(); }
    public void setArithmosDiefthinsis(String value) { arithmosDiefthinsis.set(value); }
    public StringProperty arithmosDiefthinsis Property() { return arithmosDiefthinsis; }

    // ΤΚ
    public String getTk() { return tk.get(); }
    public void setTk(String value) { tk.set(value); }
    public StringProperty tkProperty() { return tk; }

    // Timestamps
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    @Override
    public String toString() {
        return "Pelatis{" +
                "kodikosPelati='" + getKodikosPelati() + '\'' +
                ", kategoria='" + getKategoria() + '\'' +
                ", eponymiaEtairias='" + getEponymiaEtairias() + '\'' +
                '}';
    }
}
