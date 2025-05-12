package org.example.bilabonnement.service;

import org.example.bilabonnement.model.Skade;
import org.example.bilabonnement.repository.SkadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkadeService {

    @Autowired
    private SkadeRepository skadeRepository;

    public void createSkade(Skade skade) {
        skadeRepository.createSkade(skade);
    }

    public void deleteSkade(Skade skade) {
        skadeRepository.deleteSkade(skade);
    }

    public void updateSkade(Skade skade) {
        skadeRepository.updateSkade(skade);
    }

    public List<Skade> getAllSkader() {
        return skadeRepository.fetchAllSkader();
    }
}
