package com.company.repository;

import com.company.dto.ProfileDTO;
import com.company.dto.SavatchaDTO;
import com.company.dto.UserDetailDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class UserDetailRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Integer createProfile(UserDetailDTO dto) {

        String sql = "insert into user_detail(userid) values(?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, dto.getUserId());
            return ps;
        }, keyHolder);

        return (Integer) keyHolder.getKeys().get("id");
    }

    public void updateOrganizationProfile(String organization, Long userId) {
        String sql = "update user_detail set organization=? where userId=?";
        jdbcTemplate.update(sql, organization, userId);
    }

    public void updatePositionProfile(String position, Long userId) {
        String sql = "update user_detail set position=? where userId=?";
        jdbcTemplate.update(sql, position, userId);
    }

    public void updateSalaryProfile(Integer salary, Long userId) {
        String sql = "update user_detail set salary=? where userId=?";
        jdbcTemplate.update(sql, salary, userId);
    }

    public void updateInternshipProfile(String internship, Long userId) {
        String sql = "update user_detail set internship=? where userId=?";
        jdbcTemplate.update(sql, internship, userId);
    }

    public void updatePassportPhotoScannerProfile(String passportPhotoScanner, Long userId) {
        String sql = "update user_detail set passportPhotoScanner=? where userId=?";
        jdbcTemplate.update(sql, passportPhotoScanner, userId);
    }

    public void updatePassportPhotoAddressProfile(String passportPhotoAddress, Long userId) {
        String sql = "update user_detail set passportPhotoAddress=? where userId=?";
        jdbcTemplate.update(sql, passportPhotoAddress, userId);
    }

    public void updatePassportAndYouProfile(String passportAndYou, Long userId) {
        String sql = "update user_detail set passportAndYou=? where userId=?";
        jdbcTemplate.update(sql, passportAndYou, userId);
    }

    public void updatePhoneNumberProfile(String phoneNumber, Long userId) {
        String sql = "update user_detail set phoneNumber=? where userId=?";
        jdbcTemplate.update(sql, phoneNumber, userId);
    }

    public void updateAddNumberProfile(String addNumber, Long userId) {
        String sql = "update user_detail set addNumber=? where userId=?";
        jdbcTemplate.update(sql, addNumber, userId);
    }

    public void updateCardProfile(String card, Long userId) {
        String sql = "update user_detail set card=? where userId=?";
        jdbcTemplate.update(sql, card, userId);
    }

    public void updateCalidityPeriod(String validityPeriod, Long userId) {
        String sql = "update user_detail set validityPeriod=? where userId=?";
        jdbcTemplate.update(sql, validityPeriod, userId);
    }

    public void updateCardPhoneNumber(String cardPhoneNumber, Long userId) {
        String sql = "update user_detail set cardPhoneNumber=? where userId=?";
        jdbcTemplate.update(sql, cardPhoneNumber, userId);
    }

    public UserDetailDTO getByProfileId(Long id) {
        String sql = "select * from user_detail where userId=?";
        List<UserDetailDTO> profileDTOS =  jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(UserDetailDTO.class), id);

        for (UserDetailDTO dto : profileDTOS) {
            return dto;
        }

        return null;
    }

    public boolean checkProfileById(Long id) {
        String sql = "select * from user_detail where userId=?";
        List<UserDetailDTO> profileDTOS =  jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(UserDetailDTO.class), id);

        if (!profileDTOS.isEmpty()) {
            for (UserDetailDTO dto : profileDTOS) {
                return dto.getUserId().equals(id);
            }
        }
        return false;
    }
}
