package com.nxt.shell.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.*;


@Getter
@Setter
@Builder
@ToString
public class ComplaintsDto extends BaseRowModel  {

    //行政区划
    @ExcelProperty(value = "行政区划",index = 0)
    private String provinceName;

    @ExcelProperty(value = "行政区划",index = 1)
    private String cityName;

    @ExcelProperty(value = "行政区划",index = 2)
    private String  countyName;

    @ExcelProperty(value = "机构名称",index = 3)
    private String orgName; //机构名称

    @ExcelProperty(value = "通讯地址",index = 4)
    private String orgAddress; //通讯地址

    @ExcelProperty(value = "电话",index = 5)
    private String phone; // 电话

    @ExcelProperty(value = "传真",index = 6)
    private String fax; // 传真

    @ExcelProperty(value = "手机",index = 7)
    private String mobilePhone; // 手机

    @ExcelProperty(value =  "邮箱",index = 8)
    private String email;   //邮箱
}
