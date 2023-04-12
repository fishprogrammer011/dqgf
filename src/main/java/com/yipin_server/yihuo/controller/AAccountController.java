package com.yipin_server.yihuo.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yipin_server.yihuo.config.Result;
import com.yipin_server.yihuo.entity.Apply;
import com.yipin_server.yihuo.entity.Company;
import com.yipin_server.yihuo.entity.Role;
import com.yipin_server.yihuo.entity.WxUser;
import com.yipin_server.yihuo.mapper.RoleMenuMapper;
import com.yipin_server.yihuo.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: wangTao
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account")
public class AAccountController {

    private final AccountService accountService;

    private final CompanyService companyService;

    private final ApplyService applyService;

    private final RoleService roleService;

    private final AdminService adminService;

    private final MenuService menuService;

    private final RoleMenuMapper roleMenuMapper;

    @Resource
    private IUserService userService;

    //查看所有微信小程序用户
    //模糊查询
    //@AuthAccess
    @GetMapping("/selectuser")
    public Result selectUser(String openId, String nickname,
                             String phoneNumber,String startTime,String endTime,
                             @RequestParam int pageIndex,@RequestParam int pageSize){
        QueryWrapper<WxUser> wrapper = new QueryWrapper<>();
        if(StrUtil.isNotBlank(startTime)){
            wrapper.ge("register_time",startTime);
        }
        if(StrUtil.isNotBlank(endTime)){
            wrapper.le("register_time",endTime);
        }
        if(StrUtil.isNotBlank(openId)){
            wrapper.like("open_id",openId);
        }
        if(StrUtil.isNotBlank(nickname)){
            wrapper.like("nickname",nickname);
        }
        if(StrUtil.isNotBlank(phoneNumber)){
            wrapper.like("phone_number",phoneNumber);
        }
        Page<WxUser> page = new Page<>(pageIndex,pageSize);
        Page<WxUser> result = accountService.page(page,wrapper);
        Map<String,Object> map = new HashMap<>();
        map.put("result",result);
        return Result.ok().data(map);
    }

    //查看微信小程序用户详细信息
    //@AuthAccess
    @GetMapping("/lookinfo")
    public Result lookInfo(@RequestParam int id){
        WxUser wxUser = accountService.getById(id);
        int cid=wxUser.getCompanyId();
        Company company = companyService.getById(cid);
        Map<String,Object> map = new HashMap<>();
        map.put("WxUser",wxUser);
        map.put("company",company);
        return Result.ok().data(map);
    }

    //查看提交认证申请的微信小程序用户
//    @AuthAccess
    @GetMapping("/lookapplywxuser")
    public Result lookApplyWxUser(String openId, String nickname,
                                  String phoneNumber,String startTime,String endTime,
                                  @RequestParam int pageIndex,@RequestParam int pageSize){
        Page<Apply> page1 = new Page<>(pageIndex,pageSize);
        QueryWrapper<Apply> wrapper = new QueryWrapper<>();
        if(StrUtil.isNotBlank(startTime)){
            wrapper.ge("apply_time",startTime);
        }
        if(StrUtil.isNotBlank(endTime)){
            wrapper.le("apply_time",endTime);
        }
        if(StrUtil.isNotBlank(openId)){
            wrapper.like("open_id",openId);
        }
        if(StrUtil.isNotBlank(nickname)){
            wrapper.like("nickname",nickname);
        }
        if(StrUtil.isNotBlank(phoneNumber)){
            wrapper.like("wx_user_phone",phoneNumber);
        }
        wrapper.eq("status",0);
        Page<Apply> result = applyService.page(page1,wrapper);
        Map<String,Object> map = new HashMap<>();
        map.put("result",result);
        return Result.ok().data(map);
    }

    //查看认证详细信息
//    @AuthAccess
    @GetMapping("/lookapplyinfo")
    public Result lookApplyInfo(@RequestParam int id){
        Apply apply = applyService.getById(id);
        Map<String,Object> map = new HashMap<>();
        map.put("result",apply);
        return Result.ok().data(map);
    }

    //审核通过
//    @AuthAccess
    @PostMapping("/examinesuccess/{id}")
    public Result examineSuccess(@PathVariable int id){
        Apply apply = applyService.getById(id);
        apply.setStatus(1);
        applyService.saveOrUpdate(apply);
        QueryWrapper<Company> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("name",apply.getName());
        Company company = companyService.getOne(wrapper1);
        WxUser wxUser = userService.getOne(
                new QueryWrapper<WxUser>().eq("open_id",apply.getOpenId()));
        if(company==null){
            wxUser.setCRole(apply.getCrole());
            Company company1 = new Company();
            company1.setName(apply.getName())
                    .setIcon(apply.getIcon())
                    .setBgicon(apply.getBgicon())
                    .setType(apply.getType())
                    .setAddress(apply.getAddress())
                    .setDetailAddress(apply.getDetailAddress())
                    .setContactPerson(apply.getContactPerson())
                    .setPhone(apply.getPhone())
                    .setLegalPerson(apply.getLegalPerson())
                    .setLegalPersonID1(apply.getLegalPersonID1())
                    .setLegalPersonID2(apply.getLegalPersonID2())
                    .setLicense(apply.getLicense())
                    .setStatus(1).setRegtime(DateUtil.now());
            companyService.save(company1);
        }
        int cid = companyService.getOne(wrapper1).getId();
        System.out.println("cid----------------------"+cid);
        wxUser.setCompanyId(cid);
        wxUser.setRoleGrade(1);
        wxUser.setApplyId(2);
        userService.saveOrUpdate(wxUser);
        return Result.ok();
    }


    //审核不通过
//    @AuthAccess
    @PostMapping("/examinefail/{id}")
    public Result examineFail(@PathVariable int id){
        Apply apply = applyService.getById(id);
        WxUser wxUser = userService.getOne(
                new QueryWrapper<WxUser>().eq("open_id",apply.getOpenId()));
        wxUser.setApplyId(3);
        applyService.saveOrUpdate(apply);
        return Result.ok();
    }

    //查看平台所有角色
    //    @AuthAccess
//    @AuthAccess
    @GetMapping("/getallrole")
    public Result getAllRole(){
        Map<String, Object> map = new HashMap<>();
        map.put("roles",roleService.list());
        return Result.ok().data(map);
    }

//    //查看所有菜单
//    @AuthAccess
    @GetMapping("/getallmenu")
    public Result getAllMenu(){
        Map<String, Object> map = new HashMap<>();
        map.put("menus",menuService.list());
        return Result.ok().data(map);
    }

    //查看平台角色对应的菜单
//    @AuthAccess
    @GetMapping("/getrolemenu")
    public Result getRoleMenu(@RequestParam String roleFlag){
        Map<String, Object> map = new HashMap<>();
        map.put("menus",adminService.getRoleMenus(roleFlag));
        return Result.ok().data(map);
    }

    //编辑角色菜单
//    @AuthAccess
    @PostMapping("/rolemenu/{roleId}")
    public Result ediRoleMenu(@PathVariable int roleId,@RequestBody List<Integer> list){
        roleService.setRoleMenu(roleId,list);
        return Result.ok();
    }

    //删除角色
//    @AuthAccess
    @DeleteMapping("/deleterole")
    public Result deleteRole(@RequestParam int roleId){
        roleService.removeById(roleId);
        roleMenuMapper.deleteByRoleId(roleId);
        return Result.ok();
    }

    //新增角色
//    @AuthAccess
    @PostMapping("/addrole/{name}/{roleFlag}")
    public Result addRole(@PathVariable String name,@PathVariable String roleFlag,@RequestBody List<Integer> list){
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        wrapper.eq("name",name).or().eq("flag",roleFlag);
        Role role = roleService.getOne(wrapper);
        if (role!=null){
            return Result.error().message("角色已存在，无法添加");
        }else {
            roleService.save(new Role().setFlag(roleFlag).setName(name));
            Role role1 = roleService.getOne(new QueryWrapper<Role>().eq("name",name));
            roleService.setRoleMenu(role1.getId(),list);
            return Result.ok().message("角色添加成功");
        }
    }
}
