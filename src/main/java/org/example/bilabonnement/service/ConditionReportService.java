package org.example.bilabonnement.service;

import org.example.bilabonnement.model.Damage;
import org.example.bilabonnement.model.contracts.ConditionReport;
import org.example.bilabonnement.repository.CarRepository;
import org.example.bilabonnement.repository.ConditionReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConditionReportService {
    @Autowired
    private ConditionReportRepository conditionReportRepo;
    @Autowired
    private CarRepository carRepo;


    public void createReport(ConditionReport report) {
        conditionReportRepo.createConditionReport(report);
    }

    public int getLastInsertedId() {
        return conditionReportRepo.getLastInsertedId();
    }

    public ConditionReport getReportById(int id) {
        return conditionReportRepo.findConditionReportById(id);
    }

    public List<Damage> findDamagesByReportId(int reportId){
        return conditionReportRepo.findDamagesByReportId(reportId);
    }

    public void linkDamageToReport(int reportId, int damageId, String image_url) {
        conditionReportRepo.linkDamageToReport(reportId, damageId, image_url);
    }
}
