package com.company.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class UserDetailDTO {

    private Integer id;
    private Integer userId;
    private String organization;
    private String position;
    private String internship;
    private String salary;
    private String passportPhotoScanner;
    private String passportPhotoAddress;
    private String passportAndYou;
    private String phoneNumber;
    private String addNumber;
    private String card;
    private String validityPeriod;
    private String cardPhoneNumber;
    private LocalDateTime createdDate;
}
