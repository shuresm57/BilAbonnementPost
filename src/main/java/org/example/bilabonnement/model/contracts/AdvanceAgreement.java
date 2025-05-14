package org.example.bilabonnement.model.contracts;

public class AdvanceAgreement {

    private int advanceId;
    private String currency;
    private String pickupLocation;
    private boolean car_bought;

    public AdvanceAgreement() {}

    public AdvanceAgreement(int advanceId, String currency, String pickupLocation, boolean car_bought) {
        this.advanceId = advanceId;
        this.currency = currency;
        this.pickupLocation = pickupLocation;
        this.car_bought = car_bought;
    }

    public int getAdvanceId() {
        return advanceId;
    }

    public void setAdvanceId(int advanceId) {
        this.advanceId = advanceId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(String pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public boolean isCarBought() {
        return car_bought;
    }

    public void setCarBought(boolean car_bought) {
        this.car_bought = car_bought;
    }
}
