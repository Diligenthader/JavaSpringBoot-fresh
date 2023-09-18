package com.itheima.controller;

import com.itheima.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@RestController
public class UploadController {
    @PostMapping("/upload")
    public Result upload(String username, Integer age, MultipartFile image) throws IOException {
        log.info("文件上传:{},{},{}",username,age,image);

        //获取原始文件名
        String originalFilename=image.getOriginalFilename();

        //构造唯一的文件名(不能重复) --uuid(通用唯一识别码)
        int index=originalFilename.lastIndexOf(".");
        String extname=originalFilename.substring(index);//表示用于截取字符串index为起始索引之后的所有字符.
        String newFileName= UUID.randomUUID().toString()+extname;
        log.info("新的文件名:()",newFileName);

        //将文件存储在服务器的磁盘目录中 E:\images
        image.transferTo(new File("D:\\java\\"+originalFilename));
        return Result.success();
    }
}
/*
1.前端页面三要素
-表单项type="file"
-表单提交方式post
-表单的enctype属性multipart/form-data
2.服务端接收文件
MultipartFile
 */
