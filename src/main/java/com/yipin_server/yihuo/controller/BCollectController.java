package com.yipin_server.yihuo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yipin_server.yihuo.config.AuthAccess;
import com.yipin_server.yihuo.config.Result;
import com.yipin_server.yihuo.entity.Company;
import com.yipin_server.yihuo.entity.Product;
import com.yipin_server.yihuo.entity.SupplyDemand;
import com.yipin_server.yihuo.entity.UserCollect;
import com.yipin_server.yihuo.service.CompanyService;
import com.yipin_server.yihuo.service.ProductService;
import com.yipin_server.yihuo.service.SupplyDemandService;
import com.yipin_server.yihuo.service.UserCollectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: wangTao
 */
@RestController
@RequestMapping("/api/collect")
@RequiredArgsConstructor
public class BCollectController {

    private final UserCollectService userCollectService;

    private final ProductService productService;

    private final SupplyDemandService supplyDemandService;

    private final CompanyService companyService;

    //收藏或关注店铺
//    @AuthAccess
    @PostMapping("/add")
    public Result add(@RequestBody UserCollect userCollect){
        if(userCollect.getType().equals("易货")){
            Product product = productService.getById(userCollect.getId());
            int num = product.getCollectNum();
            product.setCollectNum(++num);
        }else if (userCollect.getType().equals("供求")){
            SupplyDemand supplyDemand = supplyDemandService.getById(userCollect.getId());
            int num1 = supplyDemand.getCollectNum();
            supplyDemand.setCollectNum(++num1);
        }else if (userCollect.getType().equals("店铺")){
            Company company = companyService.getById(userCollect.getId());
            int num2 = company.getCollectNum();
            company.setCollectNum(++num2);
        }
        userCollectService.save(userCollect);
        return Result.ok().message("收藏成功");
    }

    //查看我的收藏
//    @AuthAccess
    @PostMapping("/getallcollect")
    public Result getAllCollect(@RequestParam String type){
        QueryWrapper<UserCollect> wrapper = new QueryWrapper<>();
        Map map = new HashMap();
        if(type.equals("易货")){
            wrapper.eq("type",type);
            List<UserCollect> list = userCollectService.list(wrapper);
            List list1 = list.stream().map(UserCollect::getReflectId).collect(Collectors.toList());
            List<Product> list2 = productService.listByIds(list1);
            map.put("list",list2);
        }else if (type.equals("供求")){
            wrapper.eq("type",type);
            List<UserCollect> list = userCollectService.list(wrapper);
            List list1 = list.stream().map(UserCollect::getReflectId).collect(Collectors.toList());
            List<SupplyDemand> list2 = supplyDemandService.listByIds(list1);
            map.put("list",list2);

        }else if (type.equals("店铺")){
            wrapper.eq("type",type);
            List<UserCollect> list = userCollectService.list(wrapper);
            List list1 = list.stream().map(UserCollect::getReflectId).collect(Collectors.toList());
            List<Company> list2 = companyService.listByIds(list1);
            map.put("list",list2);
        }
        return Result.ok().message("收藏成功");
    }

    //取消收藏或取消关注店铺
//    @AuthAccess
    @DeleteMapping("/delete")
    public Result delete(@RequestParam Integer reflectId){
        userCollectService.removeById(reflectId);
        return Result.ok().message("取消成功");
    }
}
