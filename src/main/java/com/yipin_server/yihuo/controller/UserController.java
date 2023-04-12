package com.yipin_server.yihuo.controller;

import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.JWT;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yipin_server.yihuo.config.AuthAccess;
import com.yipin_server.yihuo.config.Result;
import com.yipin_server.yihuo.entity.*;
import com.yipin_server.yihuo.mapper.AddressMapper;
import com.yipin_server.yihuo.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequestMapping("/api/user")
@RestController
@RequiredArgsConstructor
public class UserController {

    @Resource
    private IUserService userService;

    private final AddressService addressService;

    private final ActivityService activityService;

    private final SupplyDemandService supplyDemandService;

    private final ProductService productService;

    private final ApplyService applyService;

    private final CompanyService companyService;

    private final SpendService spendService;

    private final OrderService orderService;


    //    @AuthAccess
    @GetMapping("/login")
    public Result login(@RequestParam String code, @RequestParam String encryptedData, @RequestParam String iv) throws Exception {
        System.out.println("登录----------------------------------------------------------------");
        Map<String ,Object> map = userService.login(code,encryptedData,iv);
        return Result.ok().data(map);
    }

    //获取手机号
//    @AuthAccess
        @GetMapping("/getphone")
        public Result getPhone(@RequestParam String code,@RequestParam String openId) throws Exception {
            System.out.println("登录----------------------------------------------------------------");
            QueryWrapper<WxUser> wrapper = new QueryWrapper<>();
            wrapper.eq("open_id",openId);
            WxUser wxUser = userService.getOne(wrapper);
            String phone = userService.getPhone(code);
            wxUser.setPhoneNumber(phone);
            userService.saveOrUpdate(wxUser);
            return Result.ok().message("授权成功");
        }

        //获取地址详细信息
        @GetMapping("/getwxuserinfo")
        public Result getWxWserInfo(HttpServletRequest request){
            String token1 = request.getHeader("token1");
            String openId = JWT.decode(token1).getAudience().get(0);
            QueryWrapper<WxUser> wrapper = new QueryWrapper<>();
            wrapper.eq("open_id",openId);
            WxUser wxUser = userService.getOne(wrapper);
            Map<String,Object> map = new HashMap<>();
            QueryWrapper<Company> wrapper2 = new QueryWrapper<>();
            wrapper2.eq("id",wxUser.getCompanyId());
            Company company = companyService.getOne(wrapper2);
            if (company != null){
                map.put("companyName",company.getName());
            }
            map.put("wxUser",wxUser);
            return Result.ok().data(map);
        }

    //获取所有收货地址
//    @AuthAccess
    @GetMapping("/getalladdress")
    public Result getAllAddress(@RequestParam Integer wxUserId){
        QueryWrapper<Address> wrapper = new QueryWrapper<>();
        wrapper.eq("wx_user_id",wxUserId).orderByDesc("is_default");
        Map<String,Object> map = new HashMap<>();
        map.put("address",addressService.list(wrapper));
        return Result.ok().data(map);
    }

    //获取地址详细信息
    @GetMapping("/getaddress")
    public Result getAddress(@RequestParam Integer addressId){
        QueryWrapper<Address> wrapper = new QueryWrapper<>();
        wrapper.eq("address_id",addressId);
        Map<String,Object> map = new HashMap<>();
        map.put("address",addressService.getOne(wrapper));
        return Result.ok().data(map);
    }

    //新增或更改地址
//    @AuthAccess
    @PostMapping("/saveaddress")
    public Result saveAddress(@RequestBody Address address){
        if (address.getIsDefault()==1){
            QueryWrapper<Address> wrapper = new QueryWrapper<>();
            wrapper.eq("wx_user_id",address.getWxUserId());
            List<Address> list = addressService.list(wrapper);
            list.forEach(p->p.setIsDefault(0));
            address.setIsDefault(1);
            addressService.saveOrUpdateBatch(list);
        }
        address.setCreateTime(DateUtil.now());
        addressService.saveOrUpdate(address);
        return Result.ok().message("操作成功");
    }

    //删除地址
    @DeleteMapping("/deleteaddress")
    public Result deleteAddress(@RequestParam Integer addressId){
        addressService.removeById(addressId);
        return Result.ok().message("删除成功");
    }

    //修改个人信息
//    @AuthAccess
    @PostMapping("/savewxuser")
    public Result saveAddress(@RequestBody WxUser wxUser){
        userService.saveOrUpdate(wxUser);
        return Result.ok().message("操作成功");
    }

    //申请企业认证
//    @AuthAccess
    @PostMapping("/apply")
    public Result apply(@RequestBody Apply apply, HttpServletRequest request){
        System.out.println("========申请认证=========");
        System.out.println(apply);
        String token1 = request.getHeader("token1");
        String openId = JWT.decode(token1).getAudience().get(0);
        QueryWrapper<WxUser> wrapper = new QueryWrapper<>();
        wrapper.eq("open_id",openId);
        WxUser wxUser = userService.getOne(wrapper);
        apply.setStatus(0).setOpenId(wxUser.getOpenId())
        .setNickname(wxUser.getNickname()).setPhone(wxUser.getPhoneNumber())
        .setAvatarUrl(wxUser.getAvatarUrl());
        wxUser.setCRole(apply.getCrole());
        wxUser.setApplyId(1);
        userService.saveOrUpdate(wxUser);
        applyService.save(apply);
        return Result.ok();
    }

//    //修改个人信息
//    @AuthAccess
//    @PostMapping("/savewxuser")
//    public Result saveAddress(@RequestBody WxUser wxUser){
//        userService.saveOrUpdate(wxUser);
//        return Result.ok().message("操作成功");
//    }

    //查看资金流水
    @GetMapping("/getspends")
    public Result getSpends(@RequestParam Integer companyId,@RequestParam Integer id){
        WxUser wxUser = userService.getById(id);
        if(wxUser.getCRole().equals("管理员")){
            QueryWrapper<Spend> wrapper = new QueryWrapper<>();
            wrapper.eq("company_id",companyId);
            List<Spend> list = spendService.list(wrapper);
            return Result.ok();
        }else {
            return Result.error().message("仅企业管理员可查看");
        }
    }

    //我买到的
    @GetMapping("/getmybuy")
    public Result getMyBuy(@RequestParam Integer id) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        //已付款
        wrapper.eq("status", 1);
        wrapper.eq("wx_user_id", id);
        List<Order> list = orderService.list(wrapper);
        //List<Integer> ids = list.stream().map(order->order.getProductId()).collect(Collectors.toList());
        //
        Map<String, Object> map = new HashMap<>();
        map.put("order", list);
        return Result.ok().data(map);
    }

    //我卖出的
    @GetMapping("/getmysell")
    public Result getMySell(@RequestParam Integer id) {
        QueryWrapper<Product> wrapper = new QueryWrapper<>();
        wrapper.eq("wx_user_id", id).gt("buynum",0);
        List<Product> list = productService.list(wrapper);
        Map<String, Object> map = new HashMap<>();
        map.put("product", list);
        return Result.ok().data(map);
    }

    //我发布的
    @GetMapping("/getmypublish")
    public Result getMyPublish(@RequestParam Integer id,@RequestParam Integer status) {
        QueryWrapper<Product> wrapper = new QueryWrapper<>();
        wrapper.eq("wx_user_id", id);
        wrapper.eq("del",status);
        List<Product> list = productService.list(wrapper);
        QueryWrapper<SupplyDemand> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("wx_user_id", id);
        wrapper1.eq("del",status);
        List<SupplyDemand> list1 = supplyDemandService.list(wrapper1);
        Map<String, Object> map = new HashMap<>();
        map.put("Product", list);
        map.put("SupplyDemand", list1);
        return Result.ok().data(map);
    }

    @PostMapping("/donosell")
    public Result doNoSell(@RequestParam String name,@RequestParam Integer id){
        if(name.equals("易货")){
            Product product = productService.getById(id);
            product.setDel(2);
            productService.saveOrUpdate(product);
        }
        if(name.equals("供求")){
            SupplyDemand supplyDemand = supplyDemandService.getById(id);
            supplyDemand.setDel(2);
            supplyDemandService.saveOrUpdate(supplyDemand);
        }
        return Result.ok().message("操作成功");
    }

    @PostMapping("/deletething")
    public Result deleteThing(@RequestParam String name,@RequestParam Integer id){
        if(name.equals("易货")){
            Product product = productService.getById(id);
            product.setDel(3);
            productService.saveOrUpdate(product);
        }
        if(name.equals("供求")){
            SupplyDemand supplyDemand = supplyDemandService.getById(id);
            supplyDemand.setDel(3);
            supplyDemandService.saveOrUpdate(supplyDemand);
        }
        return Result.ok().message("操作成功");
    }
}
