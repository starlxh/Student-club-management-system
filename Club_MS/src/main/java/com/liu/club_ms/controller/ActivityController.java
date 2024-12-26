package com.liu.club_ms.controller;

import com.github.pagehelper.PageInfo;
import com.liu.club_ms.jwt.JWUtil;
import com.liu.club_ms.model.Activity;
import com.liu.club_ms.service.ActivityService;
import com.liu.club_ms.util.Response;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/activity")
public class ActivityController {
    @Resource
    private ActivityService activityService;

    // 分页查询或高级查询活动信息
    @GetMapping("/queryActivityList")
    public Response queryActivityList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit,
            Activity activity,
            HttpServletRequest request) {

        //通过token获取用户类型和ID
        String token = request.getHeader("token");
        Integer type = JWUtil.getType(token);
        PageInfo<Activity> activityPageInfo;
        if (type == 1) {
            Integer userId = JWUtil.getUserId(token);
            activityPageInfo = activityService.queryActivityList(page,
                    limit,
                    activity.getName(),
                    activity.getCreatorName(),
                    activity.getClubId(),
                    userId,
                    activity.getOrder());
        } else {
            activityPageInfo = activityService.queryActivityList(page,
                    limit,
                    activity.getName(),
                    activity.getCreatorName(),
                    activity.getClubId(),
                    null,
                    activity.getOrder());
        }

        long total = activityPageInfo.getTotal();
        Response response = new Response();
        response.setData(activityPageInfo.getList());
        response.setTotal(total);
        response.setCode(20000);
        return response;
    }

    @GetMapping("/editActivity")
    public Response editActivity(@RequestBody Activity activity) {
        if(activityService.editActivity(activity))
        {
            return Response.ok();
        }
        return Response.fail("编辑活动信息失败！");
    }

    // 编辑活动状态
    @GetMapping("/editStatusById")
    public Response editStatusById(Integer status, Integer userId) {
        if (activityService.editActivityStatusById(status, userId)){
            return Response.ok();
        }else {
            return Response.fail("编辑活动状态失败");
        }
    }

    // 添加活动
    @PostMapping("/addActivity")
    public Response addActivity(@RequestBody Activity activity,
                                HttpServletRequest request){
        String token = request.getHeader("token");
        Integer userId = JWUtil.getUserId(token);
        activity.setUserId(userId);

        if(activityService.addActivity(activity)){
            return Response.ok();
        }else {
            return Response.fail("添加活动失败");
        }
    }

    // 通过ID删除活动
    @DeleteMapping("/deleteById")
    public Response deleteById(Integer activityId){
        if(activityService.deleteById(activityId)){
            return Response.ok();
        }else {
            return Response.fail("删除活动失败");
        }
    }

    // 通过用户ID查询社团活动
    @GetMapping("/queryActivityListByUserId")
    public Response queryActivityListByUserId(HttpServletRequest request){
        String token = request.getHeader("token");
        List<Activity> list = activityService.queryActivityListByUserId(JWUtil.getUserId(token));
        return Response.ok(list);
    }

    // 通过活动ID查询活动信息
    @GetMapping("/getActivityInfoById")
    public Response getActivityInfoById(Integer activityId){
        Activity activity = activityService.queryActivityListById(activityId);
        return Response.ok(activity);
    }
}