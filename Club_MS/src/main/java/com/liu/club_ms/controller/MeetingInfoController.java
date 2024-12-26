package com.liu.club_ms.controller;

import com.github.pagehelper.PageInfo;
import com.liu.club_ms.jwt.JWUtil;
import com.liu.club_ms.model.MeetingInfo;
import com.liu.club_ms.service.MeetingInfoService;
import com.liu.club_ms.util.CompareTime;
import com.liu.club_ms.util.Date2Str;
import com.liu.club_ms.util.Response;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/meeting")
public class MeetingInfoController {

    @Resource
    private MeetingInfoService meetingInfoService;

    // 分页查询或者高级查询会议记录
    @GetMapping("/queryMeetingInfoList")
    public Response queryMeetingInfoList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit,
            MeetingInfo meetingInfo,
            HttpServletRequest request) {
        //通过token获取用户类型和ID
        String token = request.getHeader("token");
        Integer type = JWUtil.getType(token);
        PageInfo<MeetingInfo> meetingInfoePageInfo;
        if (type == 1) {
            Integer userId = JWUtil.getUserId(token);
            meetingInfoePageInfo = meetingInfoService.queryMeetingInfoList(page,
                    limit,
                    meetingInfo.getRealName(),
                    meetingInfo.getName(),
                    userId,
                    meetingInfo.getClubName(),
                    meetingInfo.getOrder());
        } else {
            meetingInfoePageInfo = meetingInfoService.queryMeetingInfoList(page,
                    limit,
                    meetingInfo.getRealName(),
                    meetingInfo.getName(),
                    null,
                    meetingInfo.getClubName(),
                    meetingInfo.getOrder());
        }

        long total = meetingInfoePageInfo.getTotal();
        Response response = new Response();
        response.setData(meetingInfoePageInfo.getList());
        response.setTotal(total);
        response.setCode(20000);
        return response;
    }

    // 编辑会议记录
    @PostMapping("/editMeetingInfo")
    public Response editMeetingInfo(@RequestBody MeetingInfo meetingInfo) {
        if (meetingInfoService.editMeetingInfo(meetingInfo)){
            return Response.ok();
        }else {
            return Response.fail("编辑会议记录失败");
        }
    }

    // 添加会议记录
    @PostMapping("/addMeetingInfo")
    public Response addMeetingInfo(@RequestBody MeetingInfo meetingInfo,
                              HttpServletRequest request){
        String token = request.getHeader("token");
        meetingInfo.setUserId(JWUtil.getUserId(token));
        MeetingInfo temp = meetingInfoService.getMeetingInfoByName(meetingInfo.getName());
        if(temp != null && CompareTime.time1after2(temp.getEndTime(), Date2Str.getNowDate(new Date()))){
            return Response.fail("该会议名已存在，请等待同名会议结束或删除同名会议记录后再创建！");
        }

        if(meetingInfoService.addMeetingInfo(meetingInfo)){
            return Response.ok();
        }else {
            return Response.fail("添加会议记录失败");
        }
    }

    // 通过ID删除会议记录
    @DeleteMapping("/deleteById")
    public Response deleteById(Integer miId){
        if(meetingInfoService.deleteById(miId)){
            return Response.ok();
        }else {
            return Response.fail("删除会议记录失败");
        }
    }

    // 通过用户ID获取社团会议信息
    @GetMapping("/getMeetInfoListByUserId")
    public Response getMeetingInfoListByUserId(HttpServletRequest request){
        String token = request.getHeader("token");
        List<MeetingInfo> list = meetingInfoService.queryMeetingInfoListByUserId(JWUtil.getUserId(token));
        return Response.ok(list);
    }

    // 通过会议Id获取会议信息
    @GetMapping("/getMeetingInfoById")
    public Response getMeetingInfoByName(Integer miId){
        MeetingInfo meetingInfo = meetingInfoService.getMeetingInfoById(miId);
        return Response.ok(meetingInfo);
    }

    // 通过会议名称获取会议信息
    @GetMapping("/getMeetingInfoByName")
    public Response getMeetingInfoByName(String name){
        MeetingInfo meetingInfo = meetingInfoService.getMeetingInfoByName(name);
        return Response.ok(meetingInfo);
    }
}
