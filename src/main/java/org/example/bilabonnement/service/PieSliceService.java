package org.example.bilabonnement.service;

import org.example.bilabonnement.model.PieSlice;
import org.example.bilabonnement.model.contracts.RentalContract;
import org.example.bilabonnement.repository.RentalContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class PieSliceService {

    @Autowired
    private RentalContractRepository rentalContractRepository;


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


    /**
     *
     * en liste for at vise vores cirkeldiagram
     * hvis der er lejekontrakter i db gør den resten selv automatisk
     */
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
}
