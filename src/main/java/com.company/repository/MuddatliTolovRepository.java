package com.company.repository;

import com.company.dto.BuyurtmalarDTO;
import com.company.dto.MuddatliTolovDTO;
import com.company.dto.ProfileDTO;
import com.company.dto.SavatchaDTO;
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
public class MuddatliTolovRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Integer createProfile(MuddatliTolovDTO dto) {

        String sql = "insert into muddatli_tolov(user_id, product_id, status) values(?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, dto.getUserId());
            ps.setInt(2, dto.getProductId());
            ps.setString(3, dto.getStatus().name());
            return ps;
        }, keyHolder);

        return (Integer) keyHolder.getKeys().get("id");
    }

    public List<MuddatliTolovDTO> getByProfileIdAndProductId(Long userId, Integer productId) {
        String sql = "Select * from muddatli_tolov where user_id = " + userId + " and product_id = " + productId;
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(MuddatliTolovDTO.class));
    }

    public void updateTypeByUserIdAndProductId(String type, Long userId, Integer productId) {
        String sql = "update muddatli_tolov set type=? where user_id=? and product_id=?";
        jdbcTemplate.update(sql, type, userId, productId);
    }

    public void updatePriceByUserIdAndProductId(Integer price, Long userId, Integer productId) {
        String sql = "update muddatli_tolov set price=? where user_id=? and product_id=?";
        jdbcTemplate.update(sql, price, userId, productId);
    }

    public void updateStatusByUserId(String status, Long userId) {
        String sql = "update muddatli_tolov set status=? where user_id=? and status = 'TASDIQLANMAGAN'";
        jdbcTemplate.update(sql, status, userId);
    }

    public boolean deleteMuddatliTolov(Long userId, Integer productId) {
        String sql = "delete from muddatli_tolov where user_id = " + userId + " and product_id = " + productId;
        int n = jdbcTemplate.update(sql);
        return n > 0;
    }

    public MuddatliTolovDTO getByProfileId(Long id) {
        String sql = "select * from muddatli_tolov where user_id=? and status = 'TASDIQLANMAGAN'";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(MuddatliTolovDTO.class), id).get(0);
    }
}
