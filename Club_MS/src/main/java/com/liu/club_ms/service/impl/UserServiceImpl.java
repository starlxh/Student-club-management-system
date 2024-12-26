package com.liu.club_ms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liu.club_ms.mapper.*;
import com.liu.club_ms.model.AdminStatus;
import com.liu.club_ms.model.ApplyInfo;
import com.liu.club_ms.model.User;
import com.liu.club_ms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl
        extends ServiceImpl<UserMapper, User>
        implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ApplyMapper applyMapper;

    @Autowired
    private ClubMapper clubMapper;

    @Autowired
    private ClubMemberMapper clubMemberMapper;

    @Autowired
    private LeaveInfoMapper leaveInfoMapper;

    @Autowired
    private ActivityMapper activityMapper;

    @Autowired
    private NoticeMapper noticeMapper;

    @Autowired
    private MeetingInfoMapper meetingInfoMapper;

/* 用户管理相关 */
    // 用于分页查询或者高级查询用户信息
    @Override
    public PageInfo<User> queryUserList(Integer page,
                                        Integer limit,
                                        String realName,
                                        Integer type,
                                        String order) {
        PageHelper.startPage(page, limit);
        List<User> list  =
                userMapper.queryUserList(realName, type, order);
        return new PageInfo<>(list);
    }

    // 通过邮箱和密码查询用户
    @Override
    public User queryUserByEmailPwd(User user) {
        return userMapper.queryUserByEmailPwd(user);
    }

    // 查询邮箱是否存在
    @Override
    public boolean emailIsExist(String email) {
        return userMapper.emailIsExist(email) != null;
    }

    // 通过用户ID删除用户
    @Override
    public boolean deleteById(Integer userId, Integer type) {

        List<ApplyInfo> applyInfoList = applyMapper.queryApplyInfoIdByUserId(userId);
        if(!applyInfoList.isEmpty()){
            for (ApplyInfo applyInfo : applyInfoList) {
                applyMapper.deleteApplyListByApplyInfoId(applyInfo.getApplyInfoId());
            }
        }

        if(clubMemberMapper.deleteClubMemberByUserId(userId) <= 0 &&
                leaveInfoMapper.deleteByUserId(userId) > 0 &&
                activityMapper.updateActivityWhenHostDelete(userId) > 0)
            return false;

        if(type == 0){
            return  applyMapper.deleteApplyInfoByUserId(userId) > 0 &&
                    userMapper.deleteUserById(userId) > 0;
        }else {
            return meetingInfoMapper.updateMeetingInfoWhenDeleteUser(userId) > 0 &&
                    applyMapper.updateApplyInfoWhenDeleteAuthor(userId) > 0 &&
                    activityMapper.updateActivityWhenUserDelete(userId) > 0 &&
                    noticeMapper.updateNoticeWhenDeleteUser(userId) > 0 &&
                    clubMapper.updateClubWhenDeleteCaptain(userId) > 0 &&
                    userMapper.deleteAdminStatus(userId) > 0 &&
                    userMapper.deleteUserById(userId) > 0;
        }
    }

    // 编辑用户信息
    @Override
    public boolean editUser(User user) {
        return userMapper.editUser(user) > 0;
    }

    // 添加用户信息
    @Override
    public boolean addUser(User user) {
        return userMapper.addUser(user) > 0;
    }

    // 通过用户ID获取用户信息
    @Override
    public User getUserInfoById(Integer userId) {
        return userMapper.getUserInfoById(userId);
    }

    // 通过邮箱查找用户ID
    @Override
    public Integer queryUserIdByEmail(String email) {
        return userMapper.queryUserIdByEmail(email);
    }

    /* 管理员管理相关 */
    // 查询所有管理员
    @Override
    public PageInfo<User> queryAdminList(Integer page, Integer limit,
                                         String realName,
                                         Integer type,
                                         String order) {
        PageHelper.startPage(page, limit);
        List<User> list  =
                userMapper.queryAdminList(realName, type, order);
        return new PageInfo<>(list);
    }

    @Override
    public List<User> queryAllAdminList() {
        return userMapper.queryAllAdminList();
    }

    // 通过管理员ID删除管理员
    @Override
    public boolean deleteAdminById(Integer adminId) {
        return userMapper.deleteAdminStatus(adminId) > 0 &&
                userMapper.editUser(new User(0)) > 0;
    }

    // 编辑管理员状态
    @Override
    public boolean editAdminStatus(AdminStatus adminStatus) {
        return userMapper.editAdminStatus(adminStatus) > 0;
    }

    // 添加管理员
    @Override
    public boolean addAdmin(User user) {
        return userMapper.addAdminStatus(user.getUserId()) > 0 &&
                userMapper.editUser(user) > 0;
    }

    // 通过ID获取邮箱
    @Override
    public String getEmailByUserId(Integer userId) {
        return userMapper.getEmailByUserId(userId);
    }


}
