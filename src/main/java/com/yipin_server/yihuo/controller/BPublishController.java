package com.yipin_server.yihuo.controller;

import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.JWT;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yipin_server.yihuo.config.Result;
import com.yipin_server.yihuo.entity.*;
import com.yipin_server.yihuo.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: wangTao
 */
@RestController
@RequestMapping("/api/publish")
@RequiredArgsConstructor
public class BPublishController {

    private final ProductService productService;

    private final SupplyDemandService supplyDemandService;

    private final ActivityService activityService;

    private final IUserService userService;

    private final CompanyService companyService;

    //上传易货
    @PostMapping("/product")
    public Result postProduct(@RequestBody Product product,HttpServletRequest request){
        System.out.println("product-------------------");
        System.out.println(product);
        String token1 = request.getHeader("token1");
        String openId = JWT.decode(token1).getAudience().get(0);
        QueryWrapper<WxUser> wrapper = new QueryWrapper<>();
        wrapper.eq("open_id",openId);
        WxUser wxUser = userService.getOne(wrapper);
        Company company = companyService.getById(wxUser.getCompanyId());
        product.setCompanyName(company.getName());
        product.setCompanyId(wxUser.getCompanyId());
        product.setAddtime(DateUtil.now());
        product.setUpdatetime(DateUtil.now());
        productService.saveOrUpdate(product);
        if(wxUser.getRoleGrade() == 1) {
            wxUser.setRoleGrade(2);
            userService.saveOrUpdate(wxUser);
        }
        return Result.ok().message("上传成功，等待审核");
    }

    //上传供求
    @PostMapping("/supplydemand")
    public Result postSupplyDemand(@RequestBody SupplyDemand supplyDemand,HttpServletRequest request){
        String token1 = request.getHeader("token1");
        String openId = JWT.decode(token1).getAudience().get(0);
        QueryWrapper<WxUser> wrapper = new QueryWrapper<>();
        wrapper.eq("open_id",openId);
        WxUser wxUser = userService.getOne(wrapper);
        supplyDemand.setAddTime(DateUtil.now());
        supplyDemand.setWxUserId(wxUser.getId());
        supplyDemand.setUsername(wxUser.getNickname());
        supplyDemandService.saveOrUpdate(supplyDemand);
        if(wxUser.getRoleGrade() == 1) {
            wxUser.setRoleGrade(2);
            userService.saveOrUpdate(wxUser);
        }
        return Result.ok().message("上传成功，等待审核");
    }

    //上传活动
    @PostMapping("/activity")
    public Result postProduct(@RequestBody Activity activity,HttpServletRequest request){
        String token1 = request.getHeader("token1");
        String openId = JWT.decode(token1).getAudience().get(0);
        QueryWrapper<WxUser> wrapper = new QueryWrapper<>();
        wrapper.eq("open_id",openId);
        WxUser wxUser = userService.getOne(wrapper);
        activity.setWxUserId(wxUser.getId());
        activity.setUsername(wxUser.getNickname());
        activity.setAddtime(DateUtil.now());
        activityService.save(activity);
        if(wxUser.getRoleGrade() == 1) {
            wxUser.setRoleGrade(2);
            userService.saveOrUpdate(wxUser);
        }
        return Result.ok().message("上传成功，等待审核");
    }
}