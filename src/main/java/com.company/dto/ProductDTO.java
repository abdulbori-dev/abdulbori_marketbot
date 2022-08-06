package com.company.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductDTO {

    private Integer id;
    private Integer productCategoryId;
    private String title;
    private Long price;
    private String description;
    private String photoId;
}
