package org.example.bilabonnement.service;

import org.example.bilabonnement.model.PieSlice;
import org.example.bilabonnement.model.contracts.EndContract;
import org.example.bilabonnement.model.contracts.RentalContract;
import org.example.bilabonnement.repository.EndContractRepository;
import org.example.bilabonnement.repository.RentalContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
@Service
public class PieSliceService {

    @Autowired
    private RentalContractRepository rentalContractRepository;
    @Autowired
    private EndContractRepository endContractRepository;

    private static final String[] COLORS = {
            "#1f77b4", // muted blue
            "#ff7f0e", // safety orange
            "#2ca02c", // cooked asparagus green
            "#d62728", // brick red
            "#9467bd", // muted purple
            "#ffbb78", // pastel orange (replaces brown)
            "#e377c2", // raspberry yogurt pink
            "#7f7f7f", // middle gray
            "#bcbd22", // curry yellow-green
            "#17becf"  // blue-teal
    };

    public List<PieSlice> generateRentalSlices() {
        List<RentalContract> contracts = rentalContractRepository.fetchAllRentalContracts();
        List<PieSlice> slices = new ArrayList<>();

        double total = 0;
        for (RentalContract contract : contracts) {
            total += contract.getPrice();
        }

        if (total == 0) return slices;

        double start = 0;
        for (int i = 0; i < contracts.size(); i++) {
            RentalContract contract = contracts.get(i);
            double value = contract.getPrice();
            double end = start + (value / total);

            PieSlice slice = new PieSlice(start, end, contract.getCustomerName() + " - " + value + " kr", COLORS[i % COLORS.length]);

            slices.add(slice);
            start = end;
        }
        return slices;
    }

    /*public List<PieSlice> generatePaymentAreaSlices() {
        List<EndContract> contracts = endContractRepository.findAll();
        List<PieSlice> slices = new ArrayList<>();

        // Beregn maxDays inkl. dem med null -> days = 0
        int maxDays = contracts.stream()
                .mapToInt(c -> {
                    if (c.getDatePaid() == null || c.getDateCreated() == null) {
                        return 0;
                    }
                    LocalDate created = LocalDate.parse(c.getDateCreated());
                    LocalDate paid    = c.getDatePaid();
                    return (int) (paid.toEpochDay() - created.toEpochDay());
                })
                .max()
                .orElse(1);

        for (int i = 0; i < contracts.size(); i++) {
            EndContract contract = contracts.get(i);

            int days;
            if (contract.getDatePaid() == null || contract.getDateCreated() == null) {
                days = 0;
            } else {
                LocalDate created = LocalDate.parse(contract.getDateCreated());
                LocalDate paid    = contract.getDatePaid();
                days = (int) (paid.toEpochDay() - created.toEpochDay());
            }

            double relative = (double) days / maxDays;

            PieSlice slice = new PieSlice(
                    0.0,
                    relative,
                    "K." + contract.getContract().getContractId() + " (" + days + " d)",
                    COLORS[i % COLORS.length]
            );
            slices.add(slice);
        }
        return slices;
    }*/
}
