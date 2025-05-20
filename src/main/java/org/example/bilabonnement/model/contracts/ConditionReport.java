package org.example.bilabonnement.model.contracts;

import org.example.bilabonnement.model.Car;
import org.example.bilabonnement.model.Damage;
import org.example.bilabonnement.model.Customer;

import java.time.LocalDate;
import java.util.ArrayList;

public class ConditionReport {

    private int condition_report_id;
    private LocalDate return_date;
    private LocalDate report_date;
    private double cost;
    private int odometer;
    private int contract_id;
    private Customer customer;
    private ArrayList<Damage> damages = new ArrayList<>();
    private Car car;
    private ArrayList<Customer> customers = new ArrayList<>();

    public ConditionReport(){}


    public void setDamages(ArrayList<Damage> damages) {
        this.damages = damages;
    }

    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(ArrayList<Customer> customers) {
        this.customers = customers;
    }

    public ArrayList<Damage> getDamages(){
        return damages;
    }

    public void setDamages(Damage damage){
        this.damages = damages;
    }

    public int getContract_id() {
        return contract_id;
    }

    public int getOdometer() {
        return odometer;
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

    public void setOdometer(int odometer) {
        this.odometer = odometer;
    }

    public void setContract_id(int contract_id) {
        this.contract_id = contract_id;
    }
}
