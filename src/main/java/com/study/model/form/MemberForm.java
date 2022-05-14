package com.study.model.form;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberForm {

    private String name;
    private String password;
    private String password_check;
    private String city;
    private String street;
    private String zipcode;
}
