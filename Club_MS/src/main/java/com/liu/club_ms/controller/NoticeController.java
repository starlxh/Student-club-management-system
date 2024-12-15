package com.liu.club_ms.controller;

import com.github.pagehelper.PageInfo;
import com.liu.club_ms.jwt.JWUtil;
import com.liu.club_ms.model.Notice;
import com.liu.club_ms.service.NoticeService;
import com.liu.club_ms.util.Response;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notice")
public class NoticeController {
    @Resource
    private NoticeService noticeService;

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
                    userId);
        } else {
            noticePageInfo = noticeService.queryNoticeList(page,
                    limit,
                    notice.getRealName(),
                    notice.getTitle(),
                    null);
        }

        long total = noticePageInfo.getTotal();
        if (total > 0) {
            Response response = new Response();
            response.setData(noticePageInfo.getList());
            response.setTotal(total);
            response.setCode(20000);
            return response;
        }

        return Response.fail("查询留言信息失败！");
    }

    @PostMapping("/editNotice")
    public Response editCostList(@RequestBody Notice notice) {
        if (noticeService.editNotice(notice)){
            return Response.ok();
        }else {
            return Response.fail("编辑社团费用记录失败");
        }
    }

    @PostMapping("/addNotice")
    public Response addLeaveInfo(@RequestBody Notice notice){

        if(noticeService.addNotice(notice)){
            return Response.ok();
        }else {
            return Response.fail("添加社团费用记录失败");
        }
    }

    @DeleteMapping("/deleteById")
    public Response deleteById(Integer noticeId){
        if(noticeService.deleteById(noticeId)){
            return Response.ok();
        }else {
            return Response.fail("删除社团费用记录失败");
        }
    }


}
