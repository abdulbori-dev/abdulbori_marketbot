package com.company.service;

import com.company.dto.CategoryDTO;
import com.company.dto.ProductCategoryDTO;
import com.company.repository.CategoryRepository;
import com.company.repository.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCategoryService {
    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    public void createProfile(ProductCategoryDTO dto) {
        productCategoryRepository.createProfile(dto);

        System.out.println("Success");
    }

    public List<ProductCategoryDTO> getAllByCategoryId(Integer categoryId) {
        return productCategoryRepository.getAllByCategoryId(categoryId);
    }

    public List<ProductCategoryDTO> getAllByCategory(String callBack) {
        return productCategoryRepository.getAllByCategory(callBack);
    }

    public boolean checkCategory(String id) {
        return productCategoryRepository.checkCategory(id);
    }

    public void updateTextUz(String text) {
        productCategoryRepository.updateTextUz(text);

        System.out.println("Success");
    }

    public void updateCallbackUz(String callbackUz) {
        productCategoryRepository.updateCallbackUz(callbackUz);

        System.out.println("Success");
    }

    public void updateTextRu(String textRu) {
        productCategoryRepository.updateTextRu(textRu);

        System.out.println("Success");
    }

    public void updateCallbackKrill(String callbackKrill) {
        productCategoryRepository.updateCallBackKrill(callbackKrill);

        System.out.println("Success");
    }

    public void updateCallBackRu(String callbackRu) {
        productCategoryRepository.updateCallBackRu(callbackRu);

        System.out.println("Success");
    }

    public void updateTextKrill(String textKrill) {
        productCategoryRepository.updateTextKrill(textKrill);

        System.out.println("Success");
    }

    public void updateCategoryId(Integer categoryId) {
        productCategoryRepository.updateCategoryId(categoryId);

        System.out.println("Success");
    }

    public List<ProductCategoryDTO> getAllBy() {
        return productCategoryRepository.getAll();
    }
}
