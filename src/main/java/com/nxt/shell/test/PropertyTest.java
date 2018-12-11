package com.nxt.shell.test;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class PropertyTest {
    public static void main(String[] args) throws FileNotFoundException {
        BeanWrapper beanWrapper = new BeanWrapperImpl();
        Path path = Paths.get(System.getProperty("user.dir"),"/data");
        System.out.println(path);
        System.out.println(System.getProperty("user.dir"));
        OutputStream out = new FileOutputStream("demo.xlsx");
        try {
            ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX);
            //写第一个sheet, sheet1  数据全是List<String> 无模型映射关系
            Sheet sheet1 = new Sheet(1, 0,ExcelPropertyIndexModel.class);
            List<ExcelPropertyIndexModel> indexModels = new ArrayList<>();
            indexModels.add(new ExcelPropertyIndexModel("1","2","3"));
            indexModels.add(new ExcelPropertyIndexModel("1","2","3"));
            indexModels.add(new ExcelPropertyIndexModel("1","2","3"));
            indexModels.add(new ExcelPropertyIndexModel("1","2","3"));
            indexModels.add(new ExcelPropertyIndexModel("1","2","3"));
            indexModels.add(new ExcelPropertyIndexModel("1","2","3"));
            writer.write(indexModels, sheet1);
            writer.finish();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
