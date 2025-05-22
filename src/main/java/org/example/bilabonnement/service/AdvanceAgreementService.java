package org.example.bilabonnement.service;

import org.example.bilabonnement.model.contracts.AdvanceAgreement;
import org.example.bilabonnement.repository.AdvanceAgreementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdvanceAgreementService {

    @Autowired
    private AdvanceAgreementRepository advanceAgreementRepository;

    public List<AdvanceAgreement> getAdvanceAgreements(){
        return advanceAgreementRepository.getAllAdvanceAgreements();
    }

    public AdvanceAgreement getAdvanceAgreementById(int id){
        return advanceAgreementRepository.getAdvanceAgreementById(id);
    }

    public void saveAdvanceAgreement(AdvanceAgreement advanceAgreement){
        advanceAgreementRepository.saveAdvanceAgreement(advanceAgreement);
    }

}
