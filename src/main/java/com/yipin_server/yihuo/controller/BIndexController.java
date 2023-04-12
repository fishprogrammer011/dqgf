package com.yipin_server.yihuo.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yipin_server.yihuo.config.Result;
import com.yipin_server.yihuo.entity.*;
import com.yipin_server.yihuo.mapper.SearchHistoryMapper;
import com.yipin_server.yihuo.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import sun.nio.cs.HistoricallyNamedCharset;
import com.yipin_server.yihuo.config.AuthAccess;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: wangTao
 */
@RestController
@RequestMapping("/api/index")
@RequiredArgsConstructor
public class BIndexController {

    private final ProductService productService;

    private final CompanyService companyService;

    private final SearchHistoryMapper historyMapper;

    private final ActivityService activityService;

    //获取对应类型的易货
    @AuthAccess
    @GetMapping("/gettypeproduct")
    public Result getTypeProduct(String type) {
        QueryWrapper<Product> wrapper = new QueryWrapper<>();
        wrapper.eq("types", type);
        wrapper.eq("status", 1);
        wrapper.eq("del", 1);
        Map<String, Object> map = new HashMap<>();
        map.put("products", productService.list(wrapper));
        return Result.ok().data(map);
    }

    //模糊搜索
    @AuthAccess
    @GetMapping("/getProducts")
    public Result getProducts(String keywords, Integer id, String address, BigDecimal startPrice,
                              BigDecimal endPrice, Integer xid) {
        QueryWrapper<Product> wrapper = new QueryWrapper<>();
        if (StrUtil.isNotBlank(keywords)) {
            wrapper.like("name", keywords)
                    .or().like("intro", keywords)
                    .or().like("content", keywords)
                    .or().like("keywords", keywords);
            wrapper.eq("status", 1);
        }
        if (StrUtil.isNotBlank(address)) {
            wrapper.eq("address", address);
        }
        if (xid != null) {
            wrapper.orderByDesc("buynum");
        }
        if (startPrice != null && endPrice != null) {
            wrapper.ge("price", startPrice);
            wrapper.le("price", endPrice);
        }
        wrapper.eq("del", 1);
        Map<String, Object> map = new HashMap<>();
        map.put("products", productService.list(wrapper));
        if (id != null) {
            SearchHistory history = new SearchHistory();
            history.setKeywords(keywords);
            history.setWxUserId(id);
            historyMapper.insert(history);
        }
        return Result.ok().data(map);
    }

    //获取推荐易货
    @AuthAccess
    @GetMapping("/getadvProducts")
    public Result getAdvProducts() {
        QueryWrapper<Product> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("buynum");
        wrapper.eq("del", 1);
        Map<String, Object> map = new HashMap<>();
        map.put("products", productService.list(wrapper));
        return Result.ok().data(map);
    }

    //查看商品详细信息
    @AuthAccess
    @GetMapping("/getproductinfo")
    public Result getProductInfo(@RequestParam Integer id) {
        Map<String, Object> map = new HashMap<>();
        Product product = productService.getById(id);
        Company company = companyService.getById(product.getCompanyId());
        String companyIcon = company.getIcon();
        map.put("products", product);
        map.put("icon", companyIcon);
        return Result.ok().data(map);
    }

    //查看企业下所有商品
    @AuthAccess
    @GetMapping("/companyproduct")
    public Result getCompanyProduct(@RequestParam Integer companyId) {
        QueryWrapper<Product> wrapper = new QueryWrapper<>();
        wrapper.eq("company_id", companyId);
        wrapper.eq("del", 1);
        Map<String, Object> map = new HashMap<>();
        map.put("products", productService.list(wrapper));
        return Result.ok().data(map);
    }

    //搜索历史
    @GetMapping("/gethistory")
    public Result getHistory(@RequestParam Integer id) {
        QueryWrapper<SearchHistory> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id);
        List<SearchHistory> list = historyMapper.selectList(wrapper);
        Map<String, Object> map = new HashMap<>();
        map.put("history", list);
        return Result.ok().data(map);
    }

    //清除搜索历史
    @DeleteMapping("/deletehistory")
    public Result deleteHistory(@RequestParam Integer id) {
        historyMapper.deleteById(id);
        return Result.ok().message("删除成功");
    }

    //根据活动图片获取活动详情
    @AuthAccess
    @GetMapping("/getactivityinfo")
    public Result getActivityInfo(@RequestParam String url) {
        QueryWrapper<Activity> wrapper = new QueryWrapper<>();
        wrapper.eq("photo", url);
        wrapper.eq("del", 1);
        Activity activity = activityService.getOne(wrapper);
        Map<String, Object> map = new HashMap<>();
        map.put("activity", activity);
        return Result.ok().data(map);
    }
}
