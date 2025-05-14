package org.example.bilabonnement.service;

import org.example.bilabonnement.repository.ConditionReportDamageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConditionReportDamageService {

    @Autowired
    private ConditionReportDamageRepository conditionReportDamageRepo;

    public void linkDamageToReport(int reportId, int damageId) {
        conditionReportDamageRepo.linkDamageToReport(reportId, damageId);
    }
}
