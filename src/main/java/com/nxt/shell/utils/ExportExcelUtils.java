package com.nxt.shell.utils;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.nxt.shell.dto.ComplaintsDto;
import reactor.util.function.Tuple2;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

public abstract class ExportExcelUtils {

    private static final String DEFAULT_BASE_PATH = System.getProperty("user.dir");

    public static <T extends ComplaintsDto> Tuple2<String,List<T>> exportExcel(Tuple2<String,List<T>> tuple2) {
        Path path = Paths.get(DEFAULT_BASE_PATH, "data");
        Optional.of(path)
                .filter(p-> Files.notExists(p))
                .ifPresent(s-> {
                    try {
                        Files.createDirectories(s);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
        String filePath = path.toUri().getPath() + String.format("%s.xlsx",tuple2.getT1());
        try(OutputStream out = new FileOutputStream(filePath.substring(1))){
            ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX);
            Sheet sheet1 = new Sheet(1, 0, ComplaintsDto.class);
            writer.write(tuple2.getT2(), sheet1);
            writer.finish();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return tuple2;
    }
}
