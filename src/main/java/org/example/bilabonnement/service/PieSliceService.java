package org.example.bilabonnement.service;

import org.example.bilabonnement.model.Car;
import org.example.bilabonnement.model.PieSlice;
import org.example.bilabonnement.model.contracts.RentalContract;
import org.example.bilabonnement.repository.CarRepository;
import org.example.bilabonnement.repository.RentalContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.time.format.TextStyle;
import java.util.*;

@Service
public class PieSliceService {

    @Autowired
    private RentalContractRepository rentalContractRepository;
    @Autowired
    private CarRepository carRepository;


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
        List<RentalContract> contracts = rentalContractRepository.fetchOngoingContracts();
        List<PieSlice> slices = new ArrayList<>();

        for (int i = 0; i < contracts.size(); i++) {
            RentalContract contract = contracts.get(i);
            String label = contract.getCustomerName() + " - " + contract.getPrice() + " kr";
            String color = COLORS[i % COLORS.length];
            double value = contract.getPrice();

            slices.add(new PieSlice(value, label, color));
        }

        return slices;
    }

    public List<PieSlice> generateCarStatusSlices() {
        List<Car> allCars = carRepository.fetchAllCars();

        int countAvailable = 0;
        int countRented    = 0;
        int countDamaged   = 0;

        for (Car car : allCars) {
            String status = car.getRentalStatus();
            if ("AVAILABLE".equals(status)) {
                countAvailable++;
            } else if ("RENTED".equals(status)) {
                countRented++;
            } else if ("DAMAGED".equals(status)) {
                countDamaged++;
            }
        }

        List<PieSlice> slices = new ArrayList<>();
        if (countAvailable > 0) {
            slices.add(new PieSlice(countAvailable, "AVAILABLE", COLORS[0]));
        }
        if (countRented > 0) {
            slices.add(new PieSlice(countRented, "RENTED", COLORS[1]));
        }
        if (countDamaged > 0) {
            slices.add(new PieSlice(countDamaged, "DAMAGED", COLORS[3]));
        }
        return slices;
    }

    public List<PieSlice> getBrandRentalFrequency(){
        Map<String, Integer> frequencyMap = carRepository.getBrandRentalFrequency();
        List<PieSlice> slices = new ArrayList<>();

        List<String> brands = new ArrayList<>(frequencyMap.keySet());
        Collections.sort(brands);

        for (int i = 0; i < brands.size(); i++) {
            String brand = brands.get(i);
            int count = frequencyMap.get(brand);

            int idx = i % COLORS.length;
            String color = COLORS[idx];

            slices.add(new PieSlice(count, brand, color));
        }

        return slices;
    }

    public List<PieSlice> getMonthlyRevenue() {
        Map<Integer, Integer> revenueMap = rentalContractRepository.getMonthlyRevenue();
        List<PieSlice> slices = new ArrayList<>();

        Locale danskLocale = new Locale("da", "DK");

        for (Map.Entry<Integer, Integer> entry : revenueMap.entrySet()) {
            int monthNumber = entry.getKey();      // fx 1, 2, 3, … 12
            int revenue = entry.getValue();

            Month monthEnum = Month.of(monthNumber);

            String monthName = monthEnum.getDisplayName(TextStyle.FULL, danskLocale);

            slices.add(new PieSlice(revenue, monthName));
        }
        return slices;
    }
}
