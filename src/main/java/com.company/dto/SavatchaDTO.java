package com.company.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class SavatchaDTO {

    private Integer id;
    private Integer userId;
    private Integer productId;
    private LocalDateTime createdDate;
}
