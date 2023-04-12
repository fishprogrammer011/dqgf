package com.yipin_server.yihuo.controller;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yipin_server.yihuo.config.Result;
import com.yipin_server.yihuo.entity.*;
import com.yipin_server.yihuo.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: wangTao
 */
@RestController
@RequestMapping("/api/buy")
@RequiredArgsConstructor
public class BBuyController {

    private final OrderService orderService;

    private final CartService cartService;

    private final ProductService productService;

    private final CompanyService companyService;

    private final IUserService userService;

    //创建单个订单
    @PostMapping("/creatorder")
    public Result creatOrder(@RequestBody Order order){
        order.setCreatTime(DateUtil.now()).setStatus(0);
        orderService.save(order);
        return Result.ok();
    }

    //查看购物车
    @GetMapping("/getcart")
    public Result getCart(@RequestParam Integer id){
        QueryWrapper<Cart> wrapper = new QueryWrapper<>();
        wrapper.eq("wx_user_id",id);
        List<Cart> list = cartService.list(wrapper);
        Map<String,Object> map = new HashMap<>();
        map.put("cart",list);
        return Result.ok().data(map);
    }

    //添加易货至购物车
    @PostMapping("/addcart")
    public Result addCart(@RequestParam Integer productId
            ,@RequestParam Integer id,@RequestParam Integer num){
        Product product = productService.getById(productId);
//        QueryWrapper<Cart> wrapper = new QueryWrapper<>();
//        wrapper.eq("wx_user_id",id);
//        Cart cart = cartService.getOne(wrapper);
        if(product.getNum()<1||product.getNum()<num){
            return Result.error().message("库存不足");
        }else{
            Cart cart1 = new Cart();
            cart1.setWxUserId(id);
            BigDecimal num1 = new BigDecimal(num);
            cart1.setPrice(product.getPrice().multiply(num1));
            cart1.setProductIds(productId);
            cart1.setCreatTime(DateUtil.now());
            cartService.save(cart1);
            return Result.ok().message("添加成功");
        }
    }

    //删除购物车中产品
    @DeleteMapping("/deletecart")
    public Result deleteCart(@RequestParam Integer id,@RequestParam Integer wxUserId){
        QueryWrapper<Cart> wrapper = new QueryWrapper<>();
        wrapper.eq("product_ids",id);
        wrapper.eq("wx_user_id",wxUserId);
        cartService.remove(wrapper);
        return Result.ok().message("删除成功");
    }

    //单个订单支付
    @PostMapping("/payOrder")
    public Result payOrder(@RequestBody Order order){
        System.out.println(order);
        WxUser wxUser = userService.getById(order.getWxUserId());
        if(wxUser.getCompanyId()==null){
            return Result.error().message("未认证不能购买商品");
        }
        Company company = companyService.getById(wxUser.getCompanyId());
        BigDecimal num1 = company.getAssets();
        BigDecimal num2 = order.getTotalPrice();
        System.out.println("num1----------"+num1+"---------------num2----"+num2);
        if(num1.compareTo(num2)<0){
            return Result.error().message("余额不足，无法支付");
        }else {
            Product product = productService.getById(order.getProductId());
            int num = product.getNum();
            System.out.println("num-----------"+num);
            if(num > order.getNum()){
                int num4 = num - order.getNum();
                System.out.println("num4------"+num4);
                product.setNum(num4);
                order.setStatus(1);
                int num5 = product.getBuynum();
                System.out.println("num5-------------"+num5);
                int num6 = num5+order.getNum();
                System.out.println("num6-------------"+num6);
                product.setBuynum(num6);
                order.setCreatTime(DateUtil.now());
                order.setPayTime(DateUtil.now());
                productService.saveOrUpdate(product);
                orderService.saveOrUpdate(order);
                BigDecimal num3 = num1.subtract(num2);
                company.setAssets(num3);
                companyService.saveOrUpdate(company);
                return Result.ok().message("支付成功");
            }else {
                return Result.error().message("库存不足");
            }
        }
    }

    //购物车订单支付
    @PostMapping("/paycarter")
    public Result payCarter(@RequestBody Order order,@RequestParam Integer id){
        WxUser wxUser = userService.getById(order.getWxUserId());
        if(wxUser.getCompanyId()==null){
            return Result.error().message("未认证不能购买商品");
        }
        Company company = companyService.getById(wxUser.getCompanyId());
        BigDecimal num1 = company.getAssets();
        BigDecimal num2 = order.getTotalPrice();
        if(num1.compareTo(num2)<0){
            return Result.error().message("余额不足，无法支付");
        }else {
            Product product = productService.getById(order.getProductId());
            int num = product.getNum();
            if(num < order.getNum()){
                int num4 = num - order.getNum();
                System.out.println("num4------"+num4);
                product.setNum(num4);
                order.setStatus(1);
                int num5 = product.getBuynum();
                System.out.println("num5-------------"+num5);
                int num6 = num5+order.getNum();
                System.out.println("num6-------------"+num6);
                product.setBuynum(num6);
                productService.saveOrUpdate(product);
                orderService.saveOrUpdate(order);
                cartService.removeById(id);
                BigDecimal num3 = num1.subtract(num2);
                company.setAssets(num3);
                companyService.saveOrUpdate(company);
                return Result.ok().message("支付成功");
            }else {
                return Result.error().message("库存不足");
            }
        }
    }
}
