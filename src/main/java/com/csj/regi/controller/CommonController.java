package com.csj.regi.controller;

import com.csj.regi.common.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * 该类是专门处理文件的上传和下载
 * @author:csj
 * @version:1.0
 */
@RestController
@RequestMapping("/common")
public class CommonController {
    @Value("${img.path}")
    private String basePath;
    @PostMapping("/upload")


    //文件的上传
    public Result<String> upload(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();//获得原始文件名
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));//文件的后缀

        //使用uuid来生成文件名，防止文件名重复进行覆盖
        String fileName = UUID.randomUUID().toString()+suffix;

        File dir=new File(basePath);
        if(!dir.exists()){
            //目录不存在
            dir.mkdirs();
        }

        file.transferTo(new File(basePath+"\\"+fileName));
        return Result.success(fileName);
    }

    //文件的下载
    @GetMapping("/download")
    public void downLoad(String name, HttpServletResponse response)  {

        try {
            //获取输入和输出流
            FileInputStream fileInputStream=new FileInputStream(new File(basePath+"\\"+name));
            ServletOutputStream outputStream = response.getOutputStream();

            //给浏览器指明类型
            response.setContentType("image/jpeg");

            //读的时候的流程
            int len=0;
            byte[]bytes=new byte[1024];
            while ((len=fileInputStream.read(bytes))!=-1){
                outputStream.write(bytes,0,len);
                outputStream.flush();
            }
            //关闭输入流和输出流
            fileInputStream.close();
            outputStream.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
