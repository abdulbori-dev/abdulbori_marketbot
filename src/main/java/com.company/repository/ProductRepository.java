package com.company.repository;

import com.company.dto.ProductDTO;
import com.company.dto.ProfileDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<ProductDTO> getAllById(Integer id, Integer productCategoryId) {
        String sql = "Select * from product where category_id = " + id + " and product_category_id = " + productCategoryId;
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ProductDTO.class));
    }

    public List<ProductDTO> getProductById(Integer id) {
        String sql = "Select * from product where id = " + id;
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ProductDTO.class));
    }
}
