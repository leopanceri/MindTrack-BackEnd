package com.mindtrack.entity.dto;

import lombok.Data;

import java.util.List;

@Data
public class LoginResponseDTO {

    private Long id;
    private String name;
    private String userName;
    private String token;
    private String type = "Bearer";
    private List<String> role;

    public LoginResponseDTO(Long id, String name, String userName, String token, List<String> role) {
        this.id = id;
        this.name = name;
        this.userName = userName;
        this.token = token;
        this.role = role;
    }
}
