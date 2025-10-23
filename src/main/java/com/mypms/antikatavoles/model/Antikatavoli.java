package com.mypms.antikatavoles.model;

import javafx.beans.property.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Model κλάση για την Αντικαταβολή - JavaFX Edition με Properties
 */
public class Antikatavoli {

    private final IntegerProperty id = new SimpleIntegerProperty();
    private final IntegerProperty idApostolis = new SimpleIntegerProperty();
    private final BooleanProperty apodothike = new SimpleBooleanProperty(false);
    private final ObjectProperty<LocalDate> imerominiaApodosis = new SimpleObjectProperty<>();
    private final IntegerProperty idApodosis = new SimpleIntegerProperty();
    private final StringProperty parastatikoACS = new SimpleStringProperty();
    private final StringProperty parastatikoMyPMS = new SimpleStringProperty();
    private final StringProperty sxolia = new SimpleStringProperty();

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Antikatavoli() {}

    public Antikatavoli(Integer idApostolis) {
        setIdApostolis(idApostolis);
        setApodothike(false);
    }

    // ID
    public int getId() { return id.get(); }
    public void setId(Integer value) { id.set(value != null ? value : 0); }
    public IntegerProperty idProperty() { return id; }

    // ID Αποστολής
    public int getIdApostolis() { return idApostolis.get(); }
    public void setIdApostolis(Integer value) { idApostolis.set(value != null ? value : 0); }
    public IntegerProperty idApostolisProperty() { return idApostolis; }

    // Αποδόθηκε
    public boolean getApodothike() { return apodothike.get(); }
    public void setApodothike(Boolean value) { apodothike.set(value != null ? value : false); }
    public BooleanProperty apodothikeProperty() { return apodothike; }

    // Ημερομηνία Απόδοσης
    public LocalDate getImerominiaApodosis() { return imerominiaApodosis.get(); }
    public void setImerominiaApodosis(LocalDate value) { imerominiaApodosis.set(value); }
    public ObjectProperty<LocalDate> imerominiaApodosisProperty() { return imerominiaApodosis; }

    // ID Απόδοσης
    public int getIdApodosis() { return idApodosis.get(); }
    public void setIdApodosis(Integer value) { idApodosis.set(value != null ? value : 0); }
    public IntegerProperty idApodosisProperty() { return idApodosis; }

    // Παραστατικό ACS
    public String getParastatikoACS() { return parastatikoACS.get(); }
    public void setParastatikoACS(String value) { parastatikoACS.set(value); }
    public StringProperty parastatikoACSProperty() { return parastatikoACS; }

    // Παραστατικό MyPMS
    public String getParastatikoMyPMS() { return parastatikoMyPMS.get(); }
    public void setParastatikoMyPMS(String value) { parastatikoMyPMS.set(value); }
    public StringProperty parastatikoMyPMSProperty() { return parastatikoMyPMS; }

    // Σχόλια
    public String getSxolia() { return sxolia.get(); }
    public void setSxolia(String value) { sxolia.set(value); }
    public StringProperty sxoliaProperty() { return sxolia; }

    // Timestamps
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    @Override
    public String toString() {
        return "Antikatavoli{" +
                "id=" + getId() +
                ", idApostolis=" + getIdApostolis() +
                ", apodothike=" + getApodothike() +
                '}';
    }
}
