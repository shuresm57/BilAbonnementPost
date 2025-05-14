package org.example.bilabonnement.service;

import org.example.bilabonnement.model.contracts.ConditionReport;
import org.example.bilabonnement.repository.ConditionReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConditionReportService {
    @Autowired
    private ConditionReportRepository conditionReportRepo;

    public void createReport(ConditionReport report) {
        conditionReportRepo.createConditionReport(report);
    }

    public int getLastInsertedId() {
        return conditionReportRepo.getLastInsertedId();
    }


}
