package com.nxt.shell.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;


@Data
@Entity
@Table(name = "area_manage")
public class AreaManage implements Serializable {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
    @Column(name = "kindid")
    private String id;            //类型编号

    private String xzdm; //行政区划代码

    @Column(name = "kindname")
    private String name;        //类型编号

    @Column(name = "HASCHILD")
    private String hasChild;    //是否有下级 (1 有 0 没有)

    @Column(name = "PARENTID")
    private String parentId;    //上级编号

    private String deleted;        //删除标志

    private String des;            //描述

    private String bh;//地区编号

    private Integer px;//排序

    private String  jqcode;//久其接口省份编号

}
