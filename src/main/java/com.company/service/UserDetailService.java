package com.company.service;

import com.company.dto.ProfileDTO;
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

    public void updateOrganizationProfile(String organization, Long userId) {
        userDetailRepository.updateOrganizationProfile(organization, userId);

        System.out.println("Success");
    }

    public void updatePositionProfile(String position, Long userId) {
        userDetailRepository.updatePositionProfile(position, userId);

        System.out.println("Success");
    }

    public void updateSalaryProfile(Integer salary, Long userId) {
        userDetailRepository.updateSalaryProfile(salary, userId);

        System.out.println("Success");
    }

    public void updateInternshipProfile(String internship, Long userId) {
        userDetailRepository.updateInternshipProfile(internship, userId);

        System.out.println("Success");
    }

    public void updatePassportPhotoScannerProfile(String passportPhotoScanner, Long userId) {
        userDetailRepository.updatePassportPhotoScannerProfile(passportPhotoScanner, userId);

        System.out.println("Success");
    }

    public void updatePassportPhotoAddressProfile(String passportPhotoAddress, Long userId) {
        userDetailRepository.updatePassportPhotoAddressProfile(passportPhotoAddress, userId);

        System.out.println("Success");
    }

    public void updatePassportAndYouProfile(String passportAndYou, Long userId) {
        userDetailRepository.updatePassportAndYouProfile(passportAndYou, userId);

        System.out.println("Success");
    }

    public void updatePhoneNumberProfile(String phoneNumber, Long userId) {
        userDetailRepository.updatePhoneNumberProfile(phoneNumber, userId);

        System.out.println("Success");
    }

    public void updateAddNumberProfile(String addNumber, Long userId) {
        userDetailRepository.updateAddNumberProfile(addNumber, userId);

        System.out.println("Success");
    }

    public void updateCardProfile(String card, Long userId) {
        userDetailRepository.updateCardProfile(card, userId);

        System.out.println("Success");
    }

    public void updateCalidityPeriod(String validityPeriod, Long userId) {
        userDetailRepository.updateCalidityPeriod(validityPeriod, userId);

        System.out.println("Success");
    }

    public void updateCardPhoneNumber(String cardPhoneNumber, Long userId) {
        userDetailRepository.updateCardPhoneNumber(cardPhoneNumber, userId);

        System.out.println("Success");
    }

    public UserDetailDTO getByProfileId(Long id) {

        return userDetailRepository.getByProfileId(id);
    }

    public boolean checkProfileById(Long id) {
        return userDetailRepository.checkProfileById(id);
    }
}
