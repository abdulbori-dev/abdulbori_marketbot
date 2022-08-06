package com.company.service;

import com.company.dto.BuyurtmalarDTO;
import com.company.dto.SavatchaDTO;
import com.company.repository.SavatchaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SavatchaService {
    @Autowired
    private SavatchaRepository savatchaRepository;

    public void createProfile(SavatchaDTO dto) {
        savatchaRepository.createProfile(dto);

        System.out.println("Success");
    }

    public List<SavatchaDTO> getAllByUserId(Long userId) {
        List<SavatchaDTO> buyurtmalarList =  savatchaRepository.getAllByUserId(userId);

        return buyurtmalarList;
    }

    public void deleteSavatcha(Long userId) {
        savatchaRepository.deleteSavatcha(userId);

        System.out.println("Success");
    }
}
