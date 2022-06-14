package com.company.service;

import com.company.dto.BuyurtmalarDTO;
import com.company.enums.BuyurtmalarStatus;
import com.company.repository.BuyurtmalarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BuyurtmalarService {
    @Autowired
    private BuyurtmalarRepository buyurtmalarRepository;

    public void createProfile(BuyurtmalarDTO dto) {
        buyurtmalarRepository.createProfile(dto);

        System.out.println("Success");
    }

    public Integer getAllById(Integer productId, Integer userId) {
        List<BuyurtmalarDTO> buyurtmalarList =  buyurtmalarRepository.getAllById(productId, userId);

        for (BuyurtmalarDTO buyurtma : buyurtmalarList) {
            return buyurtma.getId();
        }

        return null;
    }

    public List<BuyurtmalarDTO> getAllByUserId(Integer userId) {
        List<BuyurtmalarDTO> buyurtmalarList =  buyurtmalarRepository.getAllByUserId(userId);

        return buyurtmalarList;
    }

    public void updateBuyurtmaStatus(String status, Integer userId, String statusWhere) {
        buyurtmalarRepository.updateBuyurtmaStatus(status, userId, statusWhere);

        System.out.println("Success");
    }

    public void updateBuyurtmaStatusId(BuyurtmalarStatus status, Integer id) {
        buyurtmalarRepository.updateBuyurtmaStatusById(String.valueOf(status), id);

        System.out.println("Success");
    }
}
