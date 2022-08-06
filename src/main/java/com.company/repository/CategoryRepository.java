package com.company.repository;

import com.company.dto.CategoryDTO;
import com.company.dto.ProductCategoryDTO;
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
public class CategoryRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Integer createProfile(CategoryDTO dto) {
        String sql = "insert into category(text_uz, callback_uz, text_ru, callback_ru, text_en, callback) values(?,?,?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, dto.getTextUz());
            ps.setString(2, dto.getCallbackUz());
            ps.setString(3, dto.getTextRu());
            ps.setString(4, dto.getCallbackRu());
            ps.setString(5, dto.getTextEn());
            ps.setString(6, dto.getCallback());
            return ps;
        }, keyHolder);

        return (Integer) keyHolder.getKeys().get("id");
    }

    public List<CategoryDTO> getById(Integer id) {
        String sql = "select * from category where id=?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(CategoryDTO.class), id);
    }

    public List<CategoryDTO> getAll() {
        String sql = "Select * from category";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(CategoryDTO.class));
    }

    public boolean checkCategory(String text) {
        String sql = "select * from category " +
                " where callback_ru = '" + text + "'" +
                " or callback_uz = '" + text + "'" +
                " or callback = '" + text + "'";
        List<ProductCategoryDTO> productList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ProductCategoryDTO.class));

        return productList.isEmpty();
    }

    public void updateTextUz(String textUz) {
        String sql = "update category set text_uz=? where text_uz is null ";
        jdbcTemplate.update(sql, textUz);
    }

    public void updateCallbackUz(String callbackUz) {
        String sql = "update category set callback_uz=? where callback_uz is null ";
        jdbcTemplate.update(sql, callbackUz);
    }

    public void updateTextRu(String textRu) {
        String sql = "update category set text_ru=?  where text_ru is null ";
        jdbcTemplate.update(sql, textRu);
    }

    public void updateCallBackKrill(String callbackKrill) {
        String sql = "update category set callback=? where callback is null ";
        jdbcTemplate.update(sql, callbackKrill);
    }

    public void updateCallBackRu(String callbackRu) {
        String sql = "update category set callback_ru=? where callback_ru is null ";
        jdbcTemplate.update(sql, callbackRu);
    }

    public void updateTextKrill(String textKrill) {
        String sql = "update category set text_en=? where text_en is null ";
        jdbcTemplate.update(sql, textKrill);
    }
}
