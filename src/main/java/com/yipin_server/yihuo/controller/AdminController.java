package com.yipin_server.yihuo.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.HttpResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yipin_server.yihuo.config.AuthAccess;
import com.yipin_server.yihuo.config.Constants;
import com.yipin_server.yihuo.config.Result;
import com.yipin_server.yihuo.entity.Apply;
import com.yipin_server.yihuo.entity.User;
import com.yipin_server.yihuo.entity.dto.UserDTO;
import com.yipin_server.yihuo.service.AdminService;
import org.bouncycastle.crypto.digests.MD5Digest;
import org.springframework.web.bind.annotation.*;
import sun.security.provider.MD5;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/api/admin")
@RestController
public class AdminController {


    @Resource
    private AdminService adminService;

//    @AuthAccess
    @GetMapping("/login")
    public Result login(UserDTO userDTO) {
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();
        if (StrUtil.isBlank(username) || StrUtil.isBlank(password)) {
            return Result.error().data(Constants.CODE_400, "参数错误");
        }
        UserDTO userDTO1 = adminService.login(userDTO);
        Map<String,Object> map=new HashMap<>();
        map.put("user",userDTO1);
        return Result.ok().data(map);
    }

//    @AuthAccess
//    @GetMapping("/logout")
//    public Result logout(HttpServletRequest request) {
//
//        return Result.ok();
//    }

    //查看所有平台用户
    @GetMapping("/manageAuser")
    public Result getAllAUser(String username, String nickname,
                              String phoneNumber,String startTime,String endTime,
                              @RequestParam int pageIndex, @RequestParam int pageSize){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        if(StrUtil.isNotBlank(startTime)){
            wrapper.ge("create_time",startTime);
        }
        if(StrUtil.isNotBlank(endTime)){
            wrapper.le("create_time",endTime);
        }
        if(StrUtil.isNotBlank(username)){
            wrapper.like("username",username);
        }
        if(StrUtil.isNotBlank(nickname)){
            wrapper.like("nickname",nickname);
        }
        if(StrUtil.isNotBlank(phoneNumber)){
            wrapper.like("phone",phoneNumber);
        }
        Page<User> page = new Page<>(pageIndex,pageSize);
        Page<User> result = adminService.page(page,wrapper);
        Map<String,Object> map = new HashMap<>();
        map.put("allUser",result);
        return Result.ok().data(map);
    }

    //查看平台用户详细信息
//    @AuthAccess
    @GetMapping("/lookAinfo")
    public Result lookAInfo(@RequestParam int id){
        User user = adminService.getById(id);
        Map<String,Object> map = new HashMap<>();
        user.setPassword("");
        map.put("user",user);
        return Result.ok().data(map);
    }

//    @AuthAccess
    @PostMapping("/enableuser")
    public Result enableCompany(@RequestParam Integer id){
        User user = adminService.getById(id);
        user.setStatus(1);
        adminService.saveOrUpdate(user);
        return Result.ok();
    }

//    @AuthAccess
    @PostMapping("/forbiduser")
    public Result forbidCompany(@RequestParam Integer id ){
        User user = adminService.getById(id);
        user.setStatus(0);
        adminService.saveOrUpdate(user);
        return Result.ok();
    }

    //新增编辑平台用户详细信息
//    @AuthAccess
    @PostMapping("/save")
    public Result save(@RequestBody User user){
        if(StrUtil.isNotBlank(user.getPassword())){
            user.setPassword(SecureUtil.md5(user.getPassword()));
        }
        adminService.saveOrUpdate(user);
        return Result.ok();
    }



}
