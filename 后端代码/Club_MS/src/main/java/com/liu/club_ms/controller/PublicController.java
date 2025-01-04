package com.liu.club_ms.controller;

import com.liu.club_ms.util.Response;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/public")
public class PublicController {

    // 上传图片
    @PostMapping("/uploadImg")
    public Response uploadImg(@RequestParam(value = "file") MultipartFile file,
                              @RequestParam(value = "type") Integer type) {
        if (file.isEmpty()) {
            System.out.println("上传的图片不存在！");
        }
        //获取图片名称
        String fileName = file.getOriginalFilename();
        //获取文件后缀
        if(fileName != null && !fileName.isEmpty()){
            String suffix = fileName.substring(fileName.lastIndexOf("."));
            //随机生成一个文件名称
            fileName = UUID.randomUUID() + suffix;
            //设置文件上传路径
//            String filePath = "e://images//";
            String filePath =  "//www//server//springboot_project//club_ms//images//";

            if(type == 0){
                filePath += "user//";
            }else if(type == 1){
                filePath += "club//";
            }else if(type == 2){
                filePath += "activity//";
            }
            //判断路径是否存在
            File dest  = new File(filePath, fileName);
            if(!dest.getParentFile().exists()){
                dest.getParentFile().mkdirs();
            }
            //上传文件
            try {
                file.transferTo(dest);
            } catch (IOException e) {
                System.out.println("上传图片出错");
            }
            //设置文件最终名称并返回前端
            String name = "/images/";
            if(type == 0){
                name += "user/";
            }else if(type == 1){
                name += "club/";
            }else if(type == 2){
                name += "activity/";
            }
            name += fileName;
            return Response.ok(name);

        }else System.out.println("获取图片后缀失败！");

        return Response.fail("传输数据出错！");
    }
}
