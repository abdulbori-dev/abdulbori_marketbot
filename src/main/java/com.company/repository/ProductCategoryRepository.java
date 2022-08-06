package com.company.repository;

import com.company.dto.CategoryDTO;
import com.company.dto.ProductCategoryDTO;
import com.company.dto.ProductDTO;
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
public class ProductCategoryRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Integer createProfile(ProductCategoryDTO dto) {
        String sql = "insert into product_category(text_uz, callback_uz, text_ru, callback_ru, text_krill, callback_krill, category_id) values(?,?,?,?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, dto.getTextUz());
            ps.setString(2, dto.getCallbackUz());
            ps.setString(3, dto.getTextRu());
            ps.setString(4, dto.getCallbackRu());
            ps.setString(5, dto.getTextKrill());
            ps.setString(6, dto.getCallbackKrill());
            ps.setInt(7, 0);
            return ps;
        }, keyHolder);

        return (Integer) keyHolder.getKeys().get("id");
    }

    public List<ProductCategoryDTO> getById(Integer id) {
        String sql = "select * from product_category where id=?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ProductCategoryDTO.class), id);
    }

    public List<ProductCategoryDTO> getAllByCategoryId(Integer categoryId) {
        String sql = "Select * from product_category where category_id=?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ProductCategoryDTO.class), categoryId);
    }

    public List<ProductCategoryDTO> getAllByCategory(String callBack) {
        String sql = "select product_category.text_uz, product_category.callback_uz, " +
                " product_category.text_ru, product_category.callback_ru, product_category.text_krill, " +
                " product_category.callback_krill, product_category.category_id" +
                " from product_category " +
                " inner join category" +
                " on category.id = product_category.category_id" +
                " where category.callback_ru = '" + callBack + "'" +
                " or category.callback_uz = '" + callBack + "'" +
                " or category.callback = '" + callBack + "'";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ProductCategoryDTO.class));
    }

    public boolean checkCategory(String text) {
        String sql = "select * from product_category " +
                " where callback_ru = '" + text + "'" +
                " or callback_uz = '" + text + "'" +
                " or callback_krill = '" + text + "'";
        List<ProductCategoryDTO> productList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ProductCategoryDTO.class));

        return productList.isEmpty();
    }

    public void updateTextUz(String text) {
        String sql = "update product_category set text_uz=? where text_uz is null ";
        jdbcTemplate.update(sql, text);
    }

    public void updateCallbackUz(String callbackUz) {
        String sql = "update product_category set callback_uz=? where callback_uz is null ";
        jdbcTemplate.update(sql, callbackUz);
    }

    public void updateTextRu(String textRu) {
        String sql = "update product_category set text_ru=?  where text_ru is null ";
        jdbcTemplate.update(sql, textRu);
    }

    public void updateCallBackKrill(String callbackKrill) {
        String sql = "update product_category set callback_krill=? where callback_krill is null ";
        jdbcTemplate.update(sql, callbackKrill);
    }

    public void updateCallBackRu(String callbackRu) {
        String sql = "update product_category set callback_ru=? where callback_ru is null ";
        jdbcTemplate.update(sql, callbackRu);
    }

    public void updateTextKrill(String textKrill) {
        String sql = "update product_category set text_krill=? where text_krill is null ";
        jdbcTemplate.update(sql, textKrill);
    }

    public void updateCategoryId(Integer categoryId) {
        String sql = "update product_category set category_id=? where category_id=0";
        jdbcTemplate.update(sql, categoryId);
    }

    public List<ProductCategoryDTO> getAll() {
        String sql = "Select * from product_category";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ProductCategoryDTO.class));
    }
}
