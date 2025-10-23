package com.mypms.shipments.model;

import javafx.beans.property.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Model κλάση για την Αποστολή - JavaFX Edition με Properties
 */
public class Apostoli {

    private final IntegerProperty idApostolis = new SimpleIntegerProperty();
    private final StringProperty kodikosPelati = new SimpleStringProperty();
    private final StringProperty courier = new SimpleStringProperty();
    private final StringProperty arithmosApostolis = new SimpleStringProperty();
    private final StringProperty arithmosParaggelias = new SimpleStringProperty();
    private final ObjectProperty<LocalDate> imerominiaParalabis = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalDate> imerominiaParadosis = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalDate> imerominiaEkdosis = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalDate> imerominiaAnaxorisis = new SimpleObjectProperty<>();
    private final ObjectProperty<BigDecimal> antikatavoli = new SimpleObjectProperty<>();
    private final StringProperty paraliptis = new SimpleStringProperty();
    private final StringProperty xora = new SimpleStringProperty();
    private final StringProperty poli = new SimpleStringProperty();
    private final StringProperty tkParalipti = new SimpleStringProperty();
    private final StringProperty diefthinsi = new SimpleStringProperty();
    private final StringProperty tilefonoStathero = new SimpleStringProperty();
    private final StringProperty tilefonoKinito = new SimpleStringProperty();
    private final StringProperty istoriko = new SimpleStringProperty();
    private final StringProperty statusApostolis = new SimpleStringProperty();
    private final StringProperty statusDetails = new SimpleStringProperty();
    private final StringProperty statusMypms = new SimpleStringProperty();
    private final StringProperty sxolia = new SimpleStringProperty();

    // Additional fields
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer deliveryFlag;
    private Integer returnedFlag;
    private String nonDeliveryReasonCode;
    private Integer shipmentStatus;
    private String deliveryInfo;
    private Boolean statusLocked = false;
    private Boolean synced;

    public Apostoli() {}

    // ID Αποστολής
    public int getIdApostolis() { return idApostolis.get(); }
    public void setIdApostolis(int value) { idApostolis.set(value); }
    public IntegerProperty idApostoliProperty() { return idApostolis; }

    // Κωδικός Πελάτη
    public String getKodikosPelati() { return kodikosPelati.get(); }
    public void setKodikosPelati(String value) { kodikosPelati.set(value); }
    public StringProperty kodikosPelatiProperty() { return kodikosPelati; }

    // Courier
    public String getCourier() { return courier.get(); }
    public void setCourier(String value) { courier.set(value); }
    public StringProperty courierProperty() { return courier; }

    // Αριθμός Αποστολής
    public String getArithmosApostolis() { return arithmosApostolis.get(); }
    public void setArithmosApostolis(String value) { arithmosApostolis.set(value); }
    public StringProperty arithmosApostolisProperty() { return arithmosApostolis; }

    // Αριθμός Παραγγελίας
    public String getArithmosParaggelias() { return arithmosParaggelias.get(); }
    public void setArithmosParaggelias(String value) { arithmosParaggelias.set(value); }
    public StringProperty arithmosParaggeliasProperty() { return arithmosParaggelias; }

    // Ημερομηνία Παραλαβής
    public LocalDate getImerominiaParalabis() { return imerominiaParalabis.get(); }
    public void setImerominiaParalabis(LocalDate value) { imerominiaParalabis.set(value); }
    public ObjectProperty<LocalDate> imerominiaParalabisProperty() { return imerominiaParalabis; }

    // Ημερομηνία Παράδοσης
    public LocalDate getImerominiaParadosis() { return imerominiaParadosis.get(); }
    public void setImerominiaParadosis(LocalDate value) { imerominiaParadosis.set(value); }
    public ObjectProperty<LocalDate> imerominiaParadosisProperty() { return imerominiaParadosis; }

    // Ημερομηνία Έκδοσης
    public LocalDate getImerominiaEkdosis() { return imerominiaEkdosis.get(); }
    public void setImerominiaEkdosis(LocalDate value) { imerominiaEkdosis.set(value); }
    public ObjectProperty<LocalDate> imerominiaEkdosisProperty() { return imerominiaEkdosis; }

    // Ημερομηνία Αναχώρησης
    public LocalDate getImerominiaAnaxorisis() { return imerominiaAnaxorisis.get(); }
    public void setImerominiaAnaxorisis(LocalDate value) { imerominiaAnaxorisis.set(value); }
    public ObjectProperty<LocalDate> imerominiaAnaxorisisProperty() { return imerominiaAnaxorisis; }

    // Αντικαταβολή
    public BigDecimal getAntikatavoli() { return antikatavoli.get(); }
    public void setAntikatavoli(BigDecimal value) { antikatavoli.set(value); }
    public ObjectProperty<BigDecimal> antikataboliProperty() { return antikatavoli; }

    // Παραλήπτης
    public String getParaliptis() { return paraliptis.get(); }
    public void setParaliptis(String value) { paraliptis.set(value); }
    public StringProperty paraliptisProperty() { return paraliptis; }

    // Χώρα
    public String getXora() { return xora.get(); }
    public void setXora(String value) { xora.set(value); }
    public StringProperty xoraProperty() { return xora; }

    // Πόλη
    public String getPoli() { return poli.get(); }
    public void setPoli(String value) { poli.set(value); }
    public StringProperty poliProperty() { return poli; }

    // ΤΚ Παραλήπτη
    public String getTkParalipti() { return tkParalipti.get(); }
    public void setTkParalipti(String value) { tkParalipti.set(value); }
    public StringProperty tkParaliptiProperty() { return tkParalipti; }

    // Διεύθυνση
    public String getDiefthinsi() { return diefthinsi.get(); }
    public void setDiefthinsi(String value) { diefthinsi.set(value); }
    public StringProperty diefthinsiProperty() { return diefthinsi; }

    // Τηλέφωνο Σταθερό
    public String getTilefonoStathero() { return tilefonoStathero.get(); }
    public void setTilefonoStathero(String value) { tilefonoStathero.set(value); }
    public StringProperty tilefonoStatheroProperty() { return tilefonoStathero; }

    // Τηλέφωνο Κινητό
    public String getTilefonoKinito() { return tilefonoKinito.get(); }
    public void setTilefonoKinito(String value) { tilefonoKinito.set(value); }
    public StringProperty tilefonoKinitoProperty() { return tilefonoKinito; }

    // Ιστορικό
    public String getIstoriko() { return istoriko.get(); }
    public void setIstoriko(String value) { istoriko.set(value); }
    public StringProperty istorikoProperty() { return istoriko; }

    // Status Αποστολής
    public String getStatusApostolis() { return statusApostolis.get(); }
    public void setStatusApostolis(String value) { statusApostolis.set(value); }
    public StringProperty statusApostolisProperty() { return statusApostolis; }

    // Status Details
    public String getStatusDetails() { return statusDetails.get(); }
    public void setStatusDetails(String value) { statusDetails.set(value); }
    public StringProperty statusDetailsProperty() { return statusDetails; }

    // Status MyPMS
    public String getStatusMypms() { return statusMypms.get(); }
    public void setStatusMypms(String value) { statusMypms.set(value); }
    public StringProperty statusMypmsProperty() { return statusMypms; }

    // Σχόλια
    public String getSxolia() { return sxolia.get(); }
    public void setSxolia(String value) { sxolia.set(value); }
    public StringProperty sxoliaProperty() { return sxolia; }

    // Non-property getters/setters
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public Integer getDeliveryFlag() { return deliveryFlag; }
    public void setDeliveryFlag(Integer deliveryFlag) { this.deliveryFlag = deliveryFlag; }

    public Integer getReturnedFlag() { return returnedFlag; }
    public void setReturnedFlag(Integer returnedFlag) { this.returnedFlag = returnedFlag; }

    public String getNonDeliveryReasonCode() { return nonDeliveryReasonCode; }
    public void setNonDeliveryReasonCode(String nonDeliveryReasonCode) { this.nonDeliveryReasonCode = nonDeliveryReasonCode; }

    public Integer getShipmentStatus() { return shipmentStatus; }
    public void setShipmentStatus(Integer shipmentStatus) { this.shipmentStatus = shipmentStatus; }

    public String getDeliveryInfo() { return deliveryInfo; }
    public void setDeliveryInfo(String deliveryInfo) { this.deliveryInfo = deliveryInfo; }

    public Boolean getStatusLocked() { return statusLocked; }
    public void setStatusLocked(Boolean statusLocked) { this.statusLocked = statusLocked; }

    public Boolean getSynced() { return synced; }
    public void setSynced(Boolean synced) { this.synced = synced; }

    @Override
    public String toString() {
        return "Apostoli{" +
                "idApostolis=" + getIdApostolis() +
                ", arithmosApostolis='" + getArithmosApostolis() + '\'' +
                ", paraliptis='" + getParaliptis() + '\'' +
                ", status='" + getStatusApostolis() + '\'' +
                '}';
    }
}
