package com.company.repository;

import com.company.dto.BuyurtmalarDTO;
import com.company.dto.SavatchaDTO;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class SavatchaRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Integer createProfile(SavatchaDTO dto) {

        String sql = "insert into savatcha(userid, productid) values(?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, dto.getUserId());
            ps.setInt(2, dto.getProductId());
            return ps;
        }, keyHolder);

        return (Integer) keyHolder.getKeys().get("id");
    }

    public List<SavatchaDTO> getAllByUserId(Integer userId) {
        String sql = "Select * from savatcha where userId = " + "'" + userId + "'";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(SavatchaDTO.class));
    }

    public boolean deleteSavatcha(Integer userId) {
        String sql = "delete from savatcha where userId = " + "'" + userId + "'";
        int n = jdbcTemplate.update(sql);
        return n > 0;
    }
}
