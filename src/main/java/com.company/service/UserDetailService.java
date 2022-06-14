package com.company.service;

import com.company.dto.SavatchaDTO;
import com.company.dto.UserDetailDTO;
import com.company.repository.UserDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDetailService {
    @Autowired
    private UserDetailRepository userDetailRepository;

    public void createProfile(UserDetailDTO dto) {
        userDetailRepository.createProfile(dto);

        System.out.println("Success");
    }

    public void updateOrganizationProfile(String organization, Integer userId) {
        userDetailRepository.updateOrganizationProfile(organization, userId);

        System.out.println("Success");
    }

    public void updatePositionProfile(String position, Integer userId) {
        userDetailRepository.updatePositionProfile(position, userId);

        System.out.println("Success");
    }

    public void updateSalaryProfile(Integer salary, Integer userId) {
        userDetailRepository.updateSalaryProfile(salary, userId);

        System.out.println("Success");
    }

    public void updateInternshipProfile(String internship, Integer userId) {
        userDetailRepository.updateInternshipProfile(internship, userId);

        System.out.println("Success");
    }

    public void updatePassportPhotoScannerProfile(String passportPhotoScanner, Integer userId) {
        userDetailRepository.updatePassportPhotoScannerProfile(passportPhotoScanner, userId);

        System.out.println("Success");
    }

    public void updatePassportPhotoAddressProfile(String passportPhotoAddress, Integer userId) {
        userDetailRepository.updatePassportPhotoAddressProfile(passportPhotoAddress, userId);

        System.out.println("Success");
    }

    public void updatePassportAndYouProfile(String passportAndYou, Integer userId) {
        userDetailRepository.updatePassportAndYouProfile(passportAndYou, userId);

        System.out.println("Success");
    }

    public void updatePhoneNumberProfile(String phoneNumber, Integer userId) {
        userDetailRepository.updatePhoneNumberProfile(phoneNumber, userId);

        System.out.println("Success");
    }

    public void updateAddNumberProfile(String addNumber, Integer userId) {
        userDetailRepository.updateAddNumberProfile(addNumber, userId);

        System.out.println("Success");
    }

    public void updateCardProfile(String card, Integer userId) {
        userDetailRepository.updateCardProfile(card, userId);

        System.out.println("Success");
    }

    public void updateCalidityPeriod(String validityPeriod, Integer userId) {
        userDetailRepository.updateCalidityPeriod(validityPeriod, userId);

        System.out.println("Success");
    }

    public void updateCardPhoneNumber(String cardPhoneNumber, Integer userId) {
        userDetailRepository.updateCardPhoneNumber(cardPhoneNumber, userId);

        System.out.println("Success");
    }
}
