package com.company.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductCategoryDTO {

    private Integer id;
    private String textUz;
    private String callbackUz;
    private String textRu;
    private String callbackRu;
    private String textKrill;
    private String callbackKrill;
    private Integer categoryId;
}
