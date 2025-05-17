package org.example.bilabonnement.model;

public class Slice {
    private double start;
    private double end;
    private String label;
    private String color;

    public Slice() {
    }

    public Slice(double start, double end, String label, String color) {
        this.start = start;
        this.end = end;
        this.label = label;
        this.color = color;
    }

    public double getStart() {
        return start;
    }

    public double getEnd() {
        return end;
    }

    public void setStart(double start) {
        this.start = start;
    }

    public void setEnd(double end) {
        this.end = end;
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
