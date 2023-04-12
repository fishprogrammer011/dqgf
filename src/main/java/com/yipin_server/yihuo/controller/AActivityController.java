package com.yipin_server.yihuo.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yipin_server.yihuo.config.AuthAccess;
import com.yipin_server.yihuo.config.Result;
import com.yipin_server.yihuo.entity.Activity;
import com.yipin_server.yihuo.entity.Banner;
import com.yipin_server.yihuo.entity.Type;
import com.yipin_server.yihuo.service.ActivityService;
import com.yipin_server.yihuo.service.BannerService;
import com.yipin_server.yihuo.service.TypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: wangTao
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/activity")
public class AActivityController {

    private final ActivityService activityService;

    private final TypeService typeService;

    private final BannerService bannerService;

    @AuthAccess
    @GetMapping("/getAllType")
    public Result getAllType(){
        Map<String,Object> map = new HashMap<>();
        map.put("activity",activityService.list());
        QueryWrapper<Type> wrapper = new QueryWrapper<>();
        wrapper.eq("level2",0);
        List<String> list = typeService.list(wrapper).stream().map(Type::getType).collect(Collectors.toList());
        map.put("types",list);
        return Result.ok().data(map);
    }

    //新增/编辑活动
//    @AuthAccess
    @PostMapping("/saveact")
    public Result addActivity(@RequestBody Activity activity){
//        if(Integer.parseInt(activity.getBeginTime())<Integer.parseInt(activity.getEndTime())){
//            activityService.saveOrUpdate(activity);
//            return Result.ok();
//        }else {
//            return Result.error().message("结束日期小于开始日期");
//        }
        activityService.saveOrUpdate(activity);
        return Result.ok().message("添加成功");
    }

    //获取所有banner
//    @AuthAccess
    @GetMapping("/getallbanner")
    public Result getAllBanner(String address, String  status, @RequestParam int pageIndex, @RequestParam int pageSize){
        QueryWrapper<Banner> wrapper = new QueryWrapper<>();
        if(StrUtil.isNotBlank(address)){
            wrapper.like("address",address);
        }
        if(StrUtil.isNotBlank(status)){
            wrapper.like("status",Integer.parseInt(status));
        }
        Page<Banner> page = new Page<>(pageIndex,pageSize);
        Page<Banner> result = bannerService.page(page,wrapper);
        Map<String,Object> map = new HashMap<>();
        map.put("result",result);
        return Result.ok().data(map);
    }


    //新增/编辑banner
//    @AuthAccess
    @PostMapping("/addbanner")
    public Result addBanner(@RequestBody Banner banner){
        bannerService.saveOrUpdate(banner);
        return Result.ok();
    }

    //下架banner
    @PostMapping("/forbidbanner")
    public Result forbidBanner(@RequestBody Banner banner){
        banner.setStatus(0);
        bannerService.saveOrUpdate(banner);
        return Result.ok();
    }



}
