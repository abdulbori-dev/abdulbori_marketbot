package com.company.service;

import com.company.dto.ProductDTO;
import com.company.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<ProductDTO> getAllById(Integer id, Integer productCategoryId) {
       return productRepository.getAllById(id, productCategoryId);
    }

    public ProductDTO getProductById(Integer id) {
        List<ProductDTO> productDTOList =  productRepository.getProductById(id);

        for (ProductDTO product : productDTOList) {
            return product;
        }

        return null;
    }
}
