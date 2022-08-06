package com.company.service;

import com.company.dto.CategoryDTO;
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

    public void updateLanguageProfile(Long id, String language) {
        profileRepository.updateLanguageProfile(id, language);

        System.out.println("Success");
    }

    public void updatePhoneProfile(Long id, String phone) {
        profileRepository.updatePhoneProfile(id, phone);

        System.out.println("Success");
    }

    public void updateNameProfile(Long id, String name) {
        profileRepository.updateNameProfile(id, name);

        System.out.println("Success");
    }

    public void updateSurnameProfile(Long id, String surname) {
        profileRepository.updateSurnameProfile(id, surname);

        System.out.println("Success");
    }

    public boolean getById(Long id) {
        return profileRepository.getById(id);
    }

    public String getProfileLanguage(Long id) {
        return profileRepository.getProfileLanguage(id);
    }

    public boolean checkPhoneNumber(Long id) {
        return profileRepository.checkPhoneNumber(id);
    }

    public ProfileDTO getByProfileId(Long id) {

        return profileRepository.getByProfileId(id);
    }

    public List<ProfileDTO> getByProfileRole(String role) {

        return profileRepository.getByProfileRole(role);
    }

    public boolean checkRole(String role, Long id) {
        return profileRepository.checkRole(role, id);
    }

    public List<ProfileDTO> getAllBy() {
        return profileRepository.getAll();
    }

    public void updateRoleProfile(String role, Long id) {
        profileRepository.updateRoleProfile(role, id);

        System.out.println("Success");
    }

    public Integer getAllProfileCount() {
        return profileRepository.getAllProfileCount();
    }

    public Integer getAllProfileCountCurrentWeek() {
        return profileRepository.getAllProfileCountCurrentWeek();
    }

    public Integer getAllProfileCountCurrentDay() {
        return profileRepository.getAllProfileCountCurrentDay();
    }

    public Integer getAllProfileCountCurrentMonth() {
        return profileRepository.getAllProfileCountCurrentMonth();
    }
}
