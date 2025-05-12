package org.example.bilabonnement.service;


import org.example.bilabonnement.model.contracts.RentalContract;
import org.example.bilabonnement.repository.RentalContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RentalContractService {

    @Autowired
    private RentalContractRepository rentalContractRepository;
    public void createRentalContract(RentalContract contract) {
        rentalContractRepository.create(contract);
    }

}
