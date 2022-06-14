package com.company.repository;

import com.company.dto.SavatchaDTO;
import com.company.dto.UserDetailDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;

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
            ps.setInt(1, dto.getUserId());
            return ps;
        }, keyHolder);

        return (Integer) keyHolder.getKeys().get("id");
    }

    public void updateOrganizationProfile(String organization, Integer userId) {
        String sql = "update user_detail set organization=? where userId=?";
        jdbcTemplate.update(sql, organization, userId);
    }

    public void updatePositionProfile(String position, Integer userId) {
        String sql = "update user_detail set position=? where userId=?";
        jdbcTemplate.update(sql, position, userId);
    }

    public void updateSalaryProfile(Integer salary, Integer userId) {
        String sql = "update user_detail set salary=? where userId=?";
        jdbcTemplate.update(sql, salary, userId);
    }

    public void updateInternshipProfile(String internship, Integer userId) {
        String sql = "update user_detail set internship=? where userId=?";
        jdbcTemplate.update(sql, internship, userId);
    }

    public void updatePassportPhotoScannerProfile(String passportPhotoScanner, Integer userId) {
        String sql = "update user_detail set passportPhotoScanner=? where userId=?";
        jdbcTemplate.update(sql, passportPhotoScanner, userId);
    }

    public void updatePassportPhotoAddressProfile(String passportPhotoAddress, Integer userId) {
        String sql = "update user_detail set passportPhotoAddress=? where userId=?";
        jdbcTemplate.update(sql, passportPhotoAddress, userId);
    }

    public void updatePassportAndYouProfile(String passportAndYou, Integer userId) {
        String sql = "update user_detail set passportAndYou=? where userId=?";
        jdbcTemplate.update(sql, passportAndYou, userId);
    }

    public void updatePhoneNumberProfile(String phoneNumber, Integer userId) {
        String sql = "update user_detail set phoneNumber=? where userId=?";
        jdbcTemplate.update(sql, phoneNumber, userId);
    }

    public void updateAddNumberProfile(String addNumber, Integer userId) {
        String sql = "update user_detail set addNumber=? where userId=?";
        jdbcTemplate.update(sql, addNumber, userId);
    }

    public void updateCardProfile(String card, Integer userId) {
        String sql = "update user_detail set card=? where userId=?";
        jdbcTemplate.update(sql, card, userId);
    }

    public void updateCalidityPeriod(String validityPeriod, Integer userId) {
        String sql = "update user_detail set validityPeriod=? where userId=?";
        jdbcTemplate.update(sql, validityPeriod, userId);
    }

    public void updateCardPhoneNumber(String cardPhoneNumber, Integer userId) {
        String sql = "update user_detail set cardPhoneNumber=? where userId=?";
        jdbcTemplate.update(sql, cardPhoneNumber, userId);
    }
}
