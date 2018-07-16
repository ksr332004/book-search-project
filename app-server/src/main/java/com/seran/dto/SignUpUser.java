package com.seran.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpUser implements Serializable {

    private static final long serialVersionUID = 6870179590768682032L;

    private String email;
    private String password;
    private String name;

}