package org.example.bilabonnement.model.contracts;

import java.time.LocalDate;

public class ConditionReport {
    private int condition_report_id;
    private LocalDate return_date;
    private LocalDate report_date;
    private double cost;
    private int km_travelled;
    private int contract_id;


    public int getContract_id() {
        return contract_id;
    }

    public int getKm_travelled() {
        return km_travelled;
    }

    public double getCost() {
        return cost;
    }

    public LocalDate getReport_date() {
        return report_date;
    }

    public LocalDate getReturn_date() {
        return return_date;
    }

    public int getCondition_report_id() {
        return condition_report_id;
    }

    public void setCondition_report_id(int condition_report_id) {
        this.condition_report_id = condition_report_id;
    }

    public void setReturn_date(LocalDate return_date) {
        this.return_date = return_date;
    }

    public void setReport_date(LocalDate report_date) {
        this.report_date = report_date;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public void setKm_travelled(int km_travelled) {
        this.km_travelled = km_travelled;
    }

    public void setContract_id(int contract_id) {
        this.contract_id = contract_id;
    }
}
