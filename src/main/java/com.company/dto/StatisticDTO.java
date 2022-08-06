package com.company.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class StatisticDTO {
    private LocalDateTime date_trunc;
    private Long count;
}
