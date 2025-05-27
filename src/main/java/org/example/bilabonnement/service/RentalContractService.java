package org.example.bilabonnement.service;


import org.example.bilabonnement.model.contracts.RentalContract;
import org.example.bilabonnement.repository.RentalContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RentalContractService {

    @Autowired
    private RentalContractRepository rentalContractRepository;
    public void createRentalContract(RentalContract contract) {
        rentalContractRepository.create(contract);
    }

    public List<RentalContract> fetchAllRentalContracts(){
        return rentalContractRepository.fetchAllRentalContracts();
    }

    public List<RentalContract> fetchOngoingRentalContracts(){
        return rentalContractRepository.fetchOngoingContracts();
    }

    public List<RentalContract> fetchCompletedRentalContracts(){
        return rentalContractRepository.fetchCompletedContracts();
    }

    public RentalContract findById(int id) {
        return rentalContractRepository.findById(id);
    }

    public int getNextRentalId()
    {
        return rentalContractRepository.getNextContractId();
    }

    public void deleteById(int id) {
        rentalContractRepository.deleteById(id);
    }
}
