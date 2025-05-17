package org.example.bilabonnement.service;

import org.example.bilabonnement.model.Slice;
import org.example.bilabonnement.model.contracts.RentalContract;
import org.example.bilabonnement.repository.RentalContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class PieService {

    @Autowired
    private RentalContractRepository rentalContractRepository;

    private static final String[] COLORS = {
            "#ff6384", "#36a2eb", "#cc65fe", "#ffce56", "#4bc0c0", "#9966ff"
    };

    public List<Slice> generateSlices() {
        List<RentalContract> contracts = rentalContractRepository.fetchAllRentalContracts();
        List<Slice> slices = new ArrayList<>();

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


            /*Slice slice = new Slice();
            slice.setStart(start);
            slice.setEnd(end);
            slice.setLabel(contract.getCustomerName() + " - " + value + " kr");
            slice.setColor(COLORS[i % COLORS.length]);*/

            String label = contract.getCustomerName() + " - " + value + " kr";
            String color = COLORS[i % COLORS.length];
            Slice slice = new Slice(start, end, label, color);

            slices.add(slice);
            start = end;
        }
        return slices;
    }
}
