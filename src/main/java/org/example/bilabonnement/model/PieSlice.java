package org.example.bilabonnement.model;

public class PieSlice {

    private double value;
    private String label;
    private String color;

    public PieSlice() {
    }

    public PieSlice(double value, String label, String color) {
        this.value = value;
        this.label = label;
        this.color = color;
    }

    //Kun til line-chart
    public PieSlice(double value, String label) {
        this.value = value;
        this.label = label;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double end) {
        this.value = end;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
