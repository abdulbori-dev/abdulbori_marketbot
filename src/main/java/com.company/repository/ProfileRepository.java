package com.company.repository;

import com.company.dto.ProfileDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
public class ProfileRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Integer createProfile(ProfileDTO dto) {
        String sql = "insert into profile(id, name, surname, phone, startedDate, profilebotlanguage, profilerole, profilestatus) values(?,?,?,?,?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, dto.getId());
            ps.setString(2, dto.getName());
            ps.setString(3, dto.getSurname());
            ps.setString(4, dto.getPhone());
            ps.setTimestamp(5, Timestamp.valueOf(dto.getStartedDate()));
            //
            ps.setString(6, dto.getProfileBotLanguage().name());
            ps.setString(7, dto.getRole().name());
            ps.setString(8, dto.getStatus().name());
            return ps;
        }, keyHolder);

        return (Integer) keyHolder.getKeys().get("id");
    }

    public void updateLanguageProfile(Integer id, String language) {
        String sql = "update profile set profileBotLanguage=? where id=?";
        jdbcTemplate.update(sql, language, id);
    }

    public void updatePhoneProfile(Integer id, String phone) {
        String sql = "update profile set phone=? where id=?";
        jdbcTemplate.update(sql, phone, id);
    }

    public void updateNameProfile(Integer id, String name) {
        String sql = "update profile set name=? where id=?";
        jdbcTemplate.update(sql, name, id);
    }

    public void updateSurnameProfile(Integer id, String surname) {
        String sql = "update profile set surname=? where id=?";
        jdbcTemplate.update(sql, surname, id);
    }

    public boolean getById(Integer id) {
        String sql = "select * from profile where id=?";
        List<ProfileDTO> profileDTOS =  jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ProfileDTO.class), id);
        return profileDTOS.isEmpty();
    }

    public boolean checkPhoneNumber(Integer id) {
        String sql = "select * from profile where id=?";
        List<ProfileDTO> profileDTOS =  jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ProfileDTO.class), id);

        for (ProfileDTO dto : profileDTOS) {
            return dto.getPhone() == null;
        }
        return false;
    }

    public String getProfileLanguage(Integer id) {
        String sql = "select profilebotlanguage from profile where id=?";
        List<ProfileDTO> profileDTOS =  jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ProfileDTO.class), id);

        for (ProfileDTO dto : profileDTOS) {
            return dto.getProfileBotLanguage().name();
        }
        return null;
    }

    public ProfileDTO getByProfileId(Integer id) {
        String sql = "select * from profile where id=?";
        List<ProfileDTO> profileDTOS =  jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ProfileDTO.class), id);

        for (ProfileDTO dto : profileDTOS) {
            return dto;
        }

        return null;
    }
}
