package com.company.service;

import com.company.dto.BuyurtmalarDTO;
import com.company.dto.MuddatliTolovDTO;
import com.company.repository.MuddatliTolovRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class MuddatliTolovService {
    @Autowired
    private MuddatliTolovRepository muddatliTolovRepository;

    public void createProfile(MuddatliTolovDTO dto) {
        muddatliTolovRepository.createProfile(dto);

        System.out.println("Success");
    }

    public MuddatliTolovDTO getByUserIdAndProductId(Long id, Integer productId) {
        List<MuddatliTolovDTO> muddatliTolovList = muddatliTolovRepository.getByProfileIdAndProductId(id, productId);

        for (MuddatliTolovDTO muddatliTolov : muddatliTolovList) {
            return muddatliTolov;
        }

        return null;
    }

    public void updateTypeByUserIdAndProductId(String type, Long userId, Integer productId) {
        muddatliTolovRepository.updateTypeByUserIdAndProductId(type, userId, productId);

        System.out.println("Success");
    }

    public void updatePriceByUserIdAndProductId(Integer price, Long userId, Integer productId) {
        muddatliTolovRepository.updatePriceByUserIdAndProductId(price, userId, productId);

        System.out.println("Success");
    }

    public void updateStatusByUserId(String status, Long userId) {
        muddatliTolovRepository.updateStatusByUserId(status, userId);

        System.out.println("Success");
    }

    public void deleteMuddatliTolov(Long userId, Integer productId) {
        muddatliTolovRepository.deleteMuddatliTolov(userId, productId);

        System.out.println("Success");
    }

    public MuddatliTolovDTO getByProfileId(Long id) {

        return muddatliTolovRepository.getByProfileId(id);
    }
}
