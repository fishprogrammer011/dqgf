package com.yipin_server.yihuo.controller;

import cn.hutool.core.util.StrUtil;
import com.yipin_server.yihuo.config.AuthAccess;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yipin_server.yihuo.config.Result;
import com.yipin_server.yihuo.entity.Company;
import com.yipin_server.yihuo.entity.Product;
import com.yipin_server.yihuo.entity.SupplyDemand;
import com.yipin_server.yihuo.service.CompanyService;
import com.yipin_server.yihuo.service.ProductService;
import com.yipin_server.yihuo.service.SupplyDemandService;
import com.yipin_server.yihuo.service.TypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: wangTao
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/company")
public class ACompanyController {

    private final CompanyService companyService;

    private final ProductService productService;

    private final TypeService typeService;

    private final SupplyDemandService supplyDemandService;

    //企业信息---------------------------------------------------------
//    @AuthAccess
    @GetMapping("/getallcompany")
    public Result getAllCompany(String id, String name, String legalPerson,
                                String startTime,String endTime,
                                @RequestParam int pageIndex, @RequestParam int pageSize){
        QueryWrapper<Company> wrapper = new QueryWrapper<>();
        if(StrUtil.isNotBlank(startTime)){
            wrapper.ge("regtime",startTime);
        }
        if(StrUtil.isNotBlank(endTime)){
            wrapper.le("regtime",endTime);
        }
        if(StrUtil.isNotBlank(id)){
            wrapper.like("id",id);
        }
        if(StrUtil.isNotBlank(name)){
            wrapper.like("name",name);
        }
        if(StrUtil.isNotBlank(legalPerson)){
            wrapper.like("legal_person",legalPerson);
        }
        Page<Company> page = new Page<>(pageIndex,pageSize);
        Page<Company> result = companyService.page(page,wrapper);
        Map<String,Object> map = new HashMap<>();
        map.put("companys",result);
        return Result.ok().data(map);
    }

//    @AuthAccess
    @GetMapping("/getcompanyinfo")
    public Result getCompanyInfo(@RequestParam Integer id){
        Company company = companyService.getById(id);
        Map<String,Object> map = new HashMap<>();
        map.put("company",company);
        return Result.ok().data(map);
    }

//    @AuthAccess
    @PostMapping("/enablecompany")
    public Result enableCompany(@RequestParam Integer id){
        Company company = companyService.getById(id);
        company.setForbidden(1);
        companyService.saveOrUpdate(company);
        return Result.ok();
    }

//    @AuthAccess
    @PostMapping("/forbidcompany")
    public Result forbidCompany(@RequestParam Integer id ){
        Company company = companyService.getById(id);
        company.setForbidden(0);
        companyService.saveOrUpdate(company);
        return Result.ok();
    }

//    @AuthAccess
    @PostMapping("/save")
    public Result save(@RequestBody Company company){
        companyService.saveOrUpdate(company);
        return Result.ok();
    }


    //商品管理---------------------------------------------------------
    @AuthAccess
    @GetMapping("/getproduct")
    public Result getAllProduct(String types, String status,String name, @RequestParam int pageIndex, @RequestParam int pageSize){
        QueryWrapper<Product> wrapper = new QueryWrapper<>();
        if(StrUtil.isNotBlank(types)){
            wrapper.like("types",types);
        }
        if(StrUtil.isNotBlank(status)){
            wrapper.like("status",status);
        }
        if(StrUtil.isNotBlank(name)){
            wrapper.like("company_name",name);
        }
        wrapper.eq("del",1);
        Page<Product> page = new Page<>(pageIndex,pageSize);
        Page<Product> result = productService.page(page,wrapper);
        Map<String,Object> map = new HashMap<>();
        map.put("products",result);
        map.put("types",typeService.list());
        return Result.ok().data(map);
    }


    //供求管理---------------------------------------------------------
    //查看所有供应
    @AuthAccess
    @GetMapping("/getsupplydemand")
    public Result getSupply(String keywords,String sdType,String type, String username,String company, @RequestParam int pageIndex, @RequestParam int pageSize){
        QueryWrapper<SupplyDemand> wrapper = new QueryWrapper<>();
        wrapper.eq("sd_type",sdType);
        System.out.println(supplyDemandService.list(wrapper));
        if(StrUtil.isNotBlank(type)){
            wrapper.like("type",type);
        }
        if(StrUtil.isNotBlank(username)){
            wrapper.like("username",username);
        }
        if(StrUtil.isNotBlank(company)){
            wrapper.like("company",company);
        }
        if(StrUtil.isNotBlank(keywords)){
            wrapper.like("title",keywords).or()
            .like("content",keywords);
        }
        Page<SupplyDemand> page = new Page<>(pageIndex,pageSize);
        Page<SupplyDemand> result = supplyDemandService.page(page,wrapper);
        Map<String,Object> map = new HashMap<>();
        map.put("supplies",result);
        map.put("types",typeService.list());
        return Result.ok().data(map);
    }

//    //查看所有需求
////    @AuthAccess
//    @GetMapping("/getdemand")
//    public Result getDemand(String keywords,String type, String username,String company, @RequestParam int pageIndex, @RequestParam int pageSize){
//        QueryWrapper<SupplyDemand> wrapper = new QueryWrapper<>();
//        wrapper.eq("sd_type","需求");
//        if(StrUtil.isNotBlank(type)){
//            wrapper.like("type",type);
//        }
//        if(StrUtil.isNotBlank(username)){
//            wrapper.like("username",username);
//        }
//        if(StrUtil.isNotBlank(company)){
//            wrapper.like("company",company);
//        }
//        if(StrUtil.isNotBlank(company)){
//            wrapper.like("company",company);
//        }
//        if(StrUtil.isNotBlank(keywords)){
//            wrapper.like("title",keywords).or()
//                    .like("content",keywords);
//        }
//        Page<SupplyDemand> page = new Page<>(pageIndex,pageSize);
//        Page<SupplyDemand> result = supplyDemandService.page(page,wrapper);
//        Map<String,Object> map = new HashMap<>();
//        map.put("demands",result);
//        map.put("types",typeService.list());
//        return Result.ok().data(map);
//    }
}
