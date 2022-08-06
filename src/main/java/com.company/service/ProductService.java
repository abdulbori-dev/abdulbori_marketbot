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

    public List<ProductDTO> getAllById(String callback) {
       return productRepository.getAllByProductCategory(callback);
    }

    public ProductDTO getProductById(Integer id) {
        List<ProductDTO> productDTOList =  productRepository.getProductById(id);

        for (ProductDTO product : productDTOList) {
            return product;
        }

        return null;
    }

    public boolean checkProductCallBack(Integer id) {
        return productRepository.checkProductCallBack(id);
    }

    public void createProfile(ProductDTO product) {
        productRepository.createProfile(product);

        System.out.println("Success");
    }

    public void updateTitle(String title) {
        productRepository.updateTitle(title);

        System.out.println("Success");
    }

    public void updateDescription(String description) {
        productRepository.updateDescription(description);

        System.out.println("Success");
    }

    public void updatePhotoId(String photoId) {
        productRepository.updatePhotoId(photoId);

        System.out.println("Success");
    }

    public void updatePrice(Long price) {
        productRepository.updatePrice(price);

        System.out.println("Success");
    }

    public void updateProductCategory(Integer productCategoryId) {
        productRepository.updateProductCategory(productCategoryId);

        System.out.println("Success");
    }
}
