package com.company.repository;

import com.company.dto.CategoryDTO;
import com.company.dto.ProductCategoryDTO;
import com.company.dto.ProductDTO;
import com.company.dto.ProfileDTO;
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
public class ProductRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<ProductDTO> getAllById(Integer productCategoryId) {
        String sql = "Select * from product where product_category_id = " + productCategoryId;
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ProductDTO.class));
    }

    public List<ProductDTO> getAllByProductCategory(String callBack) {
        String sql = "select product.id, product.product_category_id, product.title, product.price, product.description, product.photoId, product.product_category_id" +
                " from product " +
                " inner join product_category" +
                " on product_category.id = product.product_category_id" +
                " where product_category.callback_ru = '" + callBack + "'" +
                " or product_category.callback_uz = '" + callBack + "'" +
                " or product_category.callback_krill = '" + callBack + "'";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ProductDTO.class));
    }

    public List<ProductDTO> getProductById(Integer id) {
        String sql = "Select * from product where id = " + id;
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ProductDTO.class));
    }

    public boolean checkProductCallBack(Integer id) {
        String sql = "select * from product " +
                "where product_category_id = " + id;
        List<ProductDTO> productList =  jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ProductDTO.class), id);

        return productList.isEmpty();
    }

    public Integer createProfile(ProductDTO dto) {
        String sql = "insert into product(title, price, description, photoId, product_category_id) values(?,?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, dto.getTitle());
            ps.setLong(2, 0);
            ps.setString(3, dto.getDescription());
            ps.setString(4, dto.getPhotoId());
            ps.setInt(5, 0);
            return ps;
        }, keyHolder);

        return (Integer) keyHolder.getKeys().get("id");
    }

    public void updateTitle(String title) {
        String sql = "update product set title=? where title is null ";
        jdbcTemplate.update(sql, title);
    }

    public void updateDescription(String description) {
        String sql = "update product set description=? where description is null ";
        jdbcTemplate.update(sql, description);
    }
    public void updatePhotoId(String photoId) {
        String sql = "update product set photoId=? where photoId is null ";
        jdbcTemplate.update(sql, photoId);
    }

    public void updatePrice(Long price) {
        String sql = "update product set price=? where price = 0 ";
        jdbcTemplate.update(sql, price);
    }

    public void updateProductCategory(Integer productCategoryId) {
        String sql = "update product set product_category_id=? where product_category_id = 0 ";
        jdbcTemplate.update(sql, productCategoryId);
    }
}
