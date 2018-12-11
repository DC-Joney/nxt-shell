package com.nxt.shell.dto;

import lombok.Data;

@Data
public class ComplaintsOrganizationDto {

    private String orgName;

    private String orgAddress;

    private String phone; // 电话

    private String fax; // 传真

    private String cellphone; // 手机

    private String email;

    private String areaName;

    private String provinceName;

    private String cityName;

    private String  countyName;

}
