package org.example.bilabonnement.service;


import org.example.bilabonnement.model.contracts.EndContract;
import org.example.bilabonnement.repository.EndContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EndContractService {

    @Autowired
    private EndContractRepository endContractRepository;

    public List<EndContract> fetchAllEndContracts(){
        return endContractRepository.findAll();
    }

}
