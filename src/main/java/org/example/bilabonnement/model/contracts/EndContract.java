package org.example.bilabonnement.model.contracts;

import org.springframework.lang.Contract;

import java.time.LocalDate;

public class EndContract {

    private int endReportId;
    private String dateCreated;
    private RentalContract rentalContract;
    private LocalDate datePaid;


    public EndContract() {}

    public EndContract(int endReportId, String dateCreated) {
        this.endReportId = endReportId;
        this.dateCreated = dateCreated;
    }

    public int getEndReportId() {
        return endReportId;
    }

    public void setEndReportId(int endReportId) {
        this.endReportId = endReportId;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public RentalContract getContract() {
        return rentalContract;
    }

    public LocalDate getDatePaid() {
        return datePaid;
    }
}
