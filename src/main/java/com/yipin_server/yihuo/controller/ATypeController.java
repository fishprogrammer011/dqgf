package com.yipin_server.yihuo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yipin_server.yihuo.config.AuthAccess;
import com.yipin_server.yihuo.config.Result;
import com.yipin_server.yihuo.entity.Type;
import com.yipin_server.yihuo.service.ActivityService;
import com.yipin_server.yihuo.service.TypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: wangTao
 */
@RestController
@RequestMapping("/api/type")
@RequiredArgsConstructor
public class ATypeController {

    private final TypeService typeService;

    private final ActivityService activityService;

    //获取所有一级菜单
    @AuthAccess
    @GetMapping("/getlevel1type")
    public Result getLevel1Type(){
        QueryWrapper<Type> wrapper = new QueryWrapper<>();
        wrapper.eq("level2",0);
        Map<String,Object> map = new HashMap<>();
        map.put("level1",typeService.list(wrapper));
        return Result.ok().data(map);
    }


    //新增一级菜单
    @AuthAccess
    @PostMapping("/addlevel1type")
    public Result addLevel1Type(@RequestParam String name){
        Type type = new Type();
        type.setType(name);
        type.setLevel2(0);
        typeService.saveOrUpdate(type);
        return Result.ok().message("添加成功");
    }

    //获取一级菜单下的二级菜单
    @AuthAccess
    @GetMapping("/getlevel2type")
    public Result getLevel2Type(@RequestParam Integer id){
        QueryWrapper<Type> wrapper = new QueryWrapper<>();
        wrapper.eq("level2",id);
        Map<String,Object> map = new HashMap<>();
        map.put("level2",typeService.list(wrapper));
        return Result.ok().data(map);
    }

    //新增二级菜单
    @AuthAccess
    @PostMapping("/addlevel2type")
    public Result addLevel2Type(@RequestParam String name,@RequestParam Integer id){
        Type type = new Type();
        type.setType(name);
        type.setLevel2(id);
        typeService.saveOrUpdate(type);
        return Result.ok().message("添加成功");
    }

    //获取二级菜单下的三级菜单
    @AuthAccess
    @GetMapping("/getlevel3type")
    public Result getLevel3Type(@RequestParam Integer id){
        QueryWrapper<Type> wrapper = new QueryWrapper<>();
        wrapper.eq("level3",id);
        Map<String,Object> map = new HashMap<>();
        map.put("level3",typeService.list(wrapper));
        return Result.ok().data(map);
    }

    //新增三级菜单
    @AuthAccess
    @PostMapping("/addlevel3type")
    public Result addLevel3Type(@RequestParam String name,@RequestParam Integer id){
        Type type = new Type();
        type.setType(name);
        type.setLevel2(-1);
        type.setLevel3(id);
        typeService.saveOrUpdate(type);
        return Result.ok().message("添加成功");
    }

    //删除菜单
    @AuthAccess
    @DeleteMapping("/deletetype")
    public Result deleteType(@RequestParam Integer id){
        QueryWrapper<Type> wrapper = new QueryWrapper<>();
        wrapper.eq("id",id).or().eq("level2",id).or().eq("level3",id);
        typeService.remove(wrapper);
        return Result.ok().message("删除成功");
    }

    //获取所有菜单
    @AuthAccess
    @GetMapping("/getalltype")
    public Result getAllType(){
        Map<String,Object> map = new HashMap<>();
        QueryWrapper<Type> wrapper = new QueryWrapper<>();
        wrapper.eq("level2",0);
        map.put("level1",typeService.list(wrapper));
        QueryWrapper<Type> wrapper1 = new QueryWrapper<>();
        wrapper1.gt("level2",0);
        map.put("level2",typeService.list(wrapper1));
        QueryWrapper<Type> wrapper2 = new QueryWrapper<>();
        wrapper2.gt("level3",0);
        map.put("level3",typeService.list(wrapper2));
        return Result.ok().data(map);
    }

}
