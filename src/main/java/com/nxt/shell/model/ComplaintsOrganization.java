package com.nxt.shell.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Builder
@Entity
@Table(name = "cms_tsjg")
@NoArgsConstructor
@AllArgsConstructor
public class ComplaintsOrganization {

    @Id
    @GeneratedValue(generator = "nxt")
    @SequenceGenerator(name = "nxt", sequenceName = "nxt_tsjg_seq")
    private int id;

    @Column(name = "ORGNAME")
    private String orgName;

    @Column(name = "ORGADDRESS")
    private String orgAddress;

    private String phone; // 电话
    private String fax; // 传真
    private String cellphone; // 手机
    private String email;

    @Column(name = "ADDDATE")
    private Date addDate;// 添加日期

    private boolean deleted = false; // 删除标记

    @Transient
    private int px;

    @Transient
    private String areaName;

    private int struts = 0; // 状态标记

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address")
    private AreaManage areaManage;


}
