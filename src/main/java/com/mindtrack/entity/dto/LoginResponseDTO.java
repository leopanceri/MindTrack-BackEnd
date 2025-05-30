package com.mindtrack.entity.dto;

import lombok.Data;

import java.util.List;

@Data
public class LoginResponseDTO {

    private Long id;
    private String name;
    private String userName;
    private List<String> role;

    public LoginResponseDTO(Long id, String name, String userName, List<String> role) {
        this.id = id;
        this.name = name;
        this.userName = userName;
        this.role = role;
    }
}
