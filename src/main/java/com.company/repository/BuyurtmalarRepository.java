package com.company.repository;

import com.company.dto.BuyurtmalarDTO;
import com.company.dto.ProductDTO;
import com.company.dto.ProfileDTO;
import com.company.dto.UserDetailDTO;
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
public class BuyurtmalarRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Integer createProfile(BuyurtmalarDTO dto) {
        String sql = "insert into buyurtmalar(userId, status, productId, createdDate) values(?,?,?,?)";
       /* int n = jdbcTemplate.update(sql, dto.getName(), dto.getSurname(),
               dto.getPhone(),  Timestamp.valueOf(dto.getCreatedDate()));*/
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, dto.getUserId());
            ps.setString(2, dto.getStatus().name());
            ps.setInt(3, dto.getProductId());
            ps.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            return ps;
        }, keyHolder);

        return (Integer) keyHolder.getKeys().get("id");
    }

    public List<BuyurtmalarDTO> getAllById(Integer productId, Long userId) {
        String sql = "Select * from buyurtmalar where userid = " + userId + " and productid = " + productId;
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(BuyurtmalarDTO.class));
    }

    public List<BuyurtmalarDTO> getAllByUserId(Long userId) {
        String sql = "Select * from buyurtmalar where userid = " + userId;
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(BuyurtmalarDTO.class));
    }

    public void updateBuyurtmaStatus(String status, Long userId, String statusWhere) {
        String sql = "update buyurtmalar set status = ? where userId = ? and status = ?;";
        jdbcTemplate.update(sql, status, userId, statusWhere);
    }

    public void updateBuyurtmaStatusById(String status, Integer id) {
        String sql = "update buyurtmalar set status=? where id=?";
        jdbcTemplate.update(sql, status, id);
    }
}
