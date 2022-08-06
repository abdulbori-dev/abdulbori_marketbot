package com.company.dto;

import com.company.enums.MuddatliTolovStatus;
import com.company.enums.MuddatliTolovType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class MuddatliTolovDTO {

    private Integer id;
    private Integer productId;
    private Long userId;
    private LocalDateTime createdDate;
    private MuddatliTolovStatus status;
    private MuddatliTolovType type;
    private Long price;
}
