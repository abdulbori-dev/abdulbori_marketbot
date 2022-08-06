package com.company.dto;

import com.company.enums.ProfileBotLanguage;
import com.company.enums.ProfileRole;
import com.company.enums.ProfileStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ProfileDTO {
    private Long id;
    private String name;
    private String surname;
    private String phone;
    private LocalDateTime startedDate;
    private ProfileBotLanguage profileBotLanguage;
    private ProfileRole role;
    private ProfileStatus status;
}
