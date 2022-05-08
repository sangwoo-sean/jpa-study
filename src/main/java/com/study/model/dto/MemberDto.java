package com.study.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.study.model.domain.Member;
import lombok.Data;

@Data
public class MemberDto {
    private Long id;
    private String name;

    @JsonIgnore private String city;
    @JsonIgnore private String street;
    @JsonIgnore private String zipcode;

    public MemberDto(Member member) {
        id = member.getId();
        name = member.getName();
//            city = member.getAddress().getCity();
//            street = member.getAddress().getStreet();
//            zipcode = member.getAddress().getZipcode();
    }
}