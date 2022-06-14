package com.company.dto;

import com.company.enums.BuyurtmalarStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class BuyurtmalarDTO {

    private Integer id;
    private Integer userId;
    private BuyurtmalarStatus status;
    private Integer productId;
    private LocalDateTime createdDate;
}
