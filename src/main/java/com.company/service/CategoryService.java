package com.company.service;

import com.company.dto.CategoryDTO;
import com.company.dto.ProfileDTO;
import com.company.dto.SavatchaDTO;
import com.company.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public void createProfile(CategoryDTO dto) {
        categoryRepository.createProfile(dto);

        System.out.println("Success");
    }

    public void updateTextUz(String textUz) {
        categoryRepository.updateTextUz(textUz);

        System.out.println("Success");
    }

    public List<CategoryDTO> getAllBy() {
        return categoryRepository.getAll();
    }

    public boolean checkCategory(String id) {
        return categoryRepository.checkCategory(id);
    }

    public void updateCallbackUz(String callbackUz) {
        categoryRepository.updateCallbackUz(callbackUz);

        System.out.println("Success");
    }

    public void updateTextRu(String textRu) {
        categoryRepository.updateTextRu(textRu);

        System.out.println("Success");
    }

    public void updateCallbackKrill(String callbackKrill) {
        categoryRepository.updateCallBackKrill(callbackKrill);

        System.out.println("Success");
    }

    public void updateCallBackRu(String callbackRu) {
        categoryRepository.updateCallBackRu(callbackRu);

        System.out.println("Success");
    }

    public void updateTextKrill(String textKrill) {
        categoryRepository.updateTextKrill(textKrill);

        System.out.println("Success");
    }
}
