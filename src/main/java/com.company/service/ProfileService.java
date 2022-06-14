package com.company.service;

import com.company.dto.ProfileDTO;
import com.company.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;

    public void createProfile(ProfileDTO dto) {
        profileRepository.createProfile(dto);

        System.out.println("Success");
    }

    public void updateLanguageProfile(Integer id, String language) {
        profileRepository.updateLanguageProfile(id, language);

        System.out.println("Success");
    }

    public void updatePhoneProfile(Integer id, String phone) {
        profileRepository.updatePhoneProfile(id, phone);

        System.out.println("Success");
    }

    public void updateNameProfile(Integer id, String name) {
        profileRepository.updateNameProfile(id, name);

        System.out.println("Success");
    }

    public void updateSurnameProfile(Integer id, String surname) {
        profileRepository.updateSurnameProfile(id, surname);

        System.out.println("Success");
    }

    public boolean getById(Integer id) {
        return profileRepository.getById(id);
    }

    public String getProfileLanguage(Integer id) {
        return profileRepository.getProfileLanguage(id);
    }

    public boolean checkPhoneNumber(Integer id) {
        return profileRepository.checkPhoneNumber(id);
    }

    public ProfileDTO getByProfileId(Integer id) {

        return profileRepository.getByProfileId(id);
    }
}
