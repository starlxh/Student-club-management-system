package com.liu.club_ms.controller;

import com.github.pagehelper.PageInfo;
import com.liu.club_ms.model.Club;
import com.liu.club_ms.service.ClubService;
import com.liu.club_ms.util.Response;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/club")
public class ClubController {
    @Resource
    private ClubService clubService;

//  高级查询和分页查询
    @GetMapping("/queryClubList")
    public Response queryClubList(@RequestParam(defaultValue = "1") Integer page,
                                  @RequestParam(defaultValue = "10") Integer limit,
                                  Club club) {
        PageInfo<Club> pageInfo = clubService.queryClubList(page, limit, club);
        long num = pageInfo.getTotal();
        if(num > 0){
            return Response.ok(num, pageInfo.getList());
        }
        return Response.fail("查询社团信息失败！");
    }

//  上传社团图片
    @PostMapping("/uploadImg")
    public Response uploadImg(@RequestParam(value = "file") MultipartFile file) {
        if (file.isEmpty()) {
            System.out.println("上传的社团图片不存在！");
        }
        //获取图片名称
        String fileName = file.getOriginalFilename();
        //获取文件后缀
        if(fileName != null && !fileName.isEmpty()){
            String suffix = fileName.substring(fileName.lastIndexOf("."));
            //随机生成一个文件名称
            fileName = UUID.randomUUID() + suffix;
            //设置文件上传路径
            String filePath = "e://images//";
            //判断路径是否存在
            File dest  = new File(filePath, fileName);
            if(!dest.getParentFile().exists()){
                dest.getParentFile().mkdirs();
            }
            //上传文件
            try {
                file.transferTo(dest);
            } catch (IOException e) {
                System.out.println("上传社团图片出错");
            }
            //设置文件最终名称并返回前端
            String name = "/images/" + fileName;
            return Response.ok(name, null);

        }else System.out.println("获取文件后缀失败！");

        return Response.fail("上传社团图片失败！");
    }

//  添加社团
    @PostMapping("/addClub")
    public Response addClub(@RequestBody Club club) {
        if(clubService.addClub(club) > 0){
            return Response.ok();
        }
        return Response.fail("添加社团失败！");
    }

//  编辑社团
    @PostMapping("/editClub")
    public Response editClub(@RequestBody Club club) {
        if(clubService.editClub(club) > 0)
        {
            return Response.ok();
        }
        return Response.fail("添加社团失败！");
    }

//  删除社团
    @DeleteMapping("/deleteById")
    public Response deleteClub(Integer clubId) {
        if(clubService.deleteClubById(clubId))
        {
            return Response.ok();
        }
        return Response.fail("删除社团失败！");
    }
}
