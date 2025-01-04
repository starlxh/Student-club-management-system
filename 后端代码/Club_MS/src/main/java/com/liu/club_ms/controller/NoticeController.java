package com.liu.club_ms.controller;

import com.github.pagehelper.PageInfo;
import com.liu.club_ms.jwt.JWUtil;
import com.liu.club_ms.model.Notice;
import com.liu.club_ms.service.NoticeService;
import com.liu.club_ms.util.Response;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notice")
public class NoticeController {
    @Resource
    private NoticeService noticeService;

    // 分页查询或者高级查询公告
    @GetMapping("/queryNoticeList")
    public Response queryNoticeList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit,
            Notice notice,
            HttpServletRequest request) {
        //通过token获取用户类型和ID
        String token = request.getHeader("token");
        Integer type = JWUtil.getType(token);
        PageInfo<Notice> noticePageInfo;
        if (type == 1) {
            Integer userId = JWUtil.getUserId(token);
            noticePageInfo = noticeService.queryNoticeList(page,
                    limit,
                    notice.getRealName(),
                    notice.getTitle(),
                    userId,
                    notice.getClubName(),
                    notice.getOrder());
        } else {
            noticePageInfo = noticeService.queryNoticeList(page,
                    limit,
                    notice.getRealName(),
                    notice.getTitle(),
                    null,
                    notice.getClubName(),
                    notice.getOrder());
        }

        long total = noticePageInfo.getTotal();
        Response response = new Response();
        response.setData(noticePageInfo.getList());
        response.setTotal(total);
        response.setCode(20000);
        return response;
    }

    // 分页查询或者高级查询公告公共接口
    @GetMapping("/queryNoticeListPublic")
    public Response queryNoticeListPublic(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit,
            HttpServletRequest request,
            HttpServletResponse resp) {

        String token = request.getHeader("token");
        resp.addHeader("token", token);
        resp.setHeader("Access-Control-Expose-Headers", "token");
        //通过token获取用户类型和ID
        PageInfo<Notice> noticePageInfo;
        noticePageInfo = noticeService.queryNoticeList(page,
                limit,
                null,
                null,
                null,
                null,
                "ASC");

        long total = noticePageInfo.getTotal();
        Response response = new Response();
        response.setData(noticePageInfo.getList());
        response.setTotal(total);
        response.setCode(20000);
        return response;
    }

    // 编辑公告
    @PostMapping("/editNotice")
    public Response editNotice(@RequestBody Notice notice) {
        if (noticeService.editNotice(notice)){
            return Response.ok();
        }else {
            return Response.fail("编辑社团费用记录失败");
        }
    }

    // 添加公告
    @PostMapping("/addNotice")
    public Response addNotice(@RequestBody Notice notice,
                                 HttpServletRequest request){
        String token = request.getHeader("token");
        notice.setUserId(JWUtil.getUserId(token));
        if(noticeService.addNotice(notice)){
            return Response.ok();
        }else {
            return Response.fail("添加社团费用记录失败");
        }
    }

    // 通过ID删除公告
    @DeleteMapping("/deleteById")
    public Response deleteById(Integer noticeId){
        if(noticeService.deleteById(noticeId)){
            return Response.ok();
        }else {
            return Response.fail("删除社团费用记录失败");
        }
    }

    // 通过ID查询公告
    @GetMapping("queryNoticeById")
    public Response queryNoticeById(Integer noticeId,
                                    HttpServletRequest request,
                                    HttpServletResponse resp){
        String token = request.getHeader("token");
        resp.addHeader("token", token);
        resp.setHeader("Access-Control-Expose-Headers", "token");
        return Response.ok(noticeService.queryNoticeById(noticeId));
    }

}
