package com.company.repository;

import com.company.dto.CategoryDTO;
import com.company.dto.ProfileDTO;
import com.company.dto.StatisticDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;
import java.util.Objects;

@Repository
public class ProfileRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Object createProfile(ProfileDTO dto) {
        String sql = "insert into profile(id, name, surname, phone, startedDate, profilebotlanguage, profilerole, profilestatus) values(?,?,?,?,?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, dto.getId());
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

        return Objects.requireNonNull(keyHolder.getKeys()).get("id");
    }

    public void updateLanguageProfile(Long id, String language) {
        String sql = "update profile set profileBotLanguage=? where id=?";
        jdbcTemplate.update(sql, language, id);
    }

    public void updatePhoneProfile(Long id, String phone) {
        String sql = "update profile set phone=? where id=?";
        jdbcTemplate.update(sql, phone, id);
    }

    public void updateNameProfile(Long id, String name) {
        String sql = "update profile set name=? where id=?";
        jdbcTemplate.update(sql, name, id);
    }

    public void updateSurnameProfile(Long id, String surname) {
        String sql = "update profile set surname=? where id=?";
        jdbcTemplate.update(sql, surname, id);
    }

    public boolean getById(Long id) {
        String sql = "select * from profile where id=?";
        List<ProfileDTO> profileDTOS =  jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ProfileDTO.class), id);
        return profileDTOS.isEmpty();
    }

    public boolean checkPhoneNumber(Long id) {
        String sql = "select * from profile where id=?";
        List<ProfileDTO> profileDTOS =  jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ProfileDTO.class), id);

        for (ProfileDTO dto : profileDTOS) {
            return dto.getPhone() == null;
        }
        return false;
    }

    public String getProfileLanguage(Long id) {
        String sql = "select profilebotlanguage from profile where id=?";
        List<ProfileDTO> profileDTOS =  jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ProfileDTO.class), id);

        for (ProfileDTO dto : profileDTOS) {
            return dto.getProfileBotLanguage().name();
        }
        return null;
    }

    public ProfileDTO getByProfileId(Long id) {
        String sql = "select * from profile where id=?";
        List<ProfileDTO> profileDTOS =  jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ProfileDTO.class), id);

        for (ProfileDTO dto : profileDTOS) {
            return dto;
        }

        return null;
    }

    public List<ProfileDTO> getByProfileRole(String role) {
        String sql = "select * from profile where profilerole=?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ProfileDTO.class), role);
    }

    public boolean checkRole(String role, Long id) {
        String sql = "select * from profile where profilerole=?";
        List<ProfileDTO> profileDTOS =  jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ProfileDTO.class), role);

        for (ProfileDTO dto : profileDTOS) {
            return dto.getId().equals(id);
        }
        return false;
    }

    public List<ProfileDTO> getAll() {
        String sql = "Select * from profile where profilerole = 'USER'";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ProfileDTO.class));
    }

    public void updateRoleProfile(String role, Long id) {
        String sql = "update profile set profilerole=? where id=?";
        jdbcTemplate.update(sql, role, id);
    }

    public Integer getAllProfileCount() {
        String sql = "select count(*) from profile";
        return Math.toIntExact(jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(StatisticDTO.class)).get(0).getCount());
    }

    public Integer getAllProfileCountCurrentWeek() {
        String sql = "select date_trunc('week', starteddate), count(*) from profile where profile.starteddate > now() - interval '1 week' group by 1;";
        return Math.toIntExact(jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(StatisticDTO.class)).get(0).getCount());
    }

    public Integer getAllProfileCountCurrentDay() {
        String sql = "select date_trunc('day', starteddate), count(*) from profile where profile.starteddate > now() - interval '1 day' group by 1;";
        return Math.toIntExact(jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(StatisticDTO.class)).get(0).getCount());
    }

    public Integer getAllProfileCountCurrentMonth() {
        String sql = "select date_trunc('month', starteddate), count(*) from profile where profile.starteddate > now() - interval '1 month' group by 1;";
        return Math.toIntExact(jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(StatisticDTO.class)).get(0).getCount());
    }
}
