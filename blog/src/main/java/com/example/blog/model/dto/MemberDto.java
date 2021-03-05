package com.example.blog.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDto {

    private String account;
    private String password;
    private String name;
    private String email;
    private String auth;
}
