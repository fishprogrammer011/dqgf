package com.yipin_server.yihuo.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yipin_server.yihuo.entity.Company;
import com.yipin_server.yihuo.entity.WxUser;
import com.yipin_server.yihuo.mapper.UserMapper;
import com.yipin_server.yihuo.service.CompanyService;
import com.yipin_server.yihuo.service.IUserService;
import com.yipin_server.yihuo.util.JwtTokenUtil;
import com.yipin_server.yihuo.util.WeChatUtil;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.net.Inet4Address;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Component
public class UserServiceImpl extends ServiceImpl<UserMapper, WxUser> implements IUserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private CompanyService companyService;

//    @Resource
//    IUserService userService;

    @Override
    public Map<String,Object> login(String code, String encryptedData, String iv) throws Exception {
        net.sf.json.JSONObject jsonObject = WeChatUtil.getSessionKeyOrOpenId(code);
        String sessionKey = jsonObject.get("session_key").toString();
        String openId = jsonObject.get("openid").toString();
        System.out.println("openId------------------"+openId);
        return getUser(encryptedData,sessionKey,iv,openId);
    }

    @Override
    public String getPhone(String code) {
        String accessToken = WeChatUtil.getAccessToken();
        net.sf.json.JSONObject phoneInfo = WeChatUtil.getPhoneNUmber(code,accessToken);
        String phoneNumber = phoneInfo.getString("phoneNumber");
        return phoneNumber;
    }


    private Map<String,Object> getUser(String encryptedData, String sessionKey, String iv, String openId) throws Exception {
        System.out.println(encryptedData+"---------"+sessionKey+"-----"+iv);
        System.out.println("解密----------------"+ com.alibaba.fastjson2.JSONObject.parseObject(WeChatUtil.decrypt(encryptedData,sessionKey,iv)));
        JSONObject userInfo = JSONObject.parseObject(WeChatUtil.decrypt(encryptedData,sessionKey,iv));
        System.out.println("userinfo------------"+userInfo);
        QueryWrapper<WxUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("open_id",openId);
        WxUser wxUser = getOne(queryWrapper);
        String token= JwtTokenUtil.genToken(openId,sessionKey);
        System.out.println(wxUser);
        Map<String ,Object> map = new HashMap<>();
        if (wxUser==null){
            assert false;
            WxUser wxUser1 = new WxUser().setOpenId(openId)
                    .setLoginIp(Inet4Address.getLocalHost().getHostAddress())
                    .setRoleGrade(0)
                    .setAvatarUrl(userInfo.getString("avatarUrl"))
                    .setNickname(userInfo.getString("nickName"))
                    .setGender(userInfo.getString("gender"))
                    .setRegisterTime(DateUtil.now())
                    .setStatus(1);
            System.out.println("wxUser--------------"+wxUser);
            userMapper.insert(wxUser1);
            map.put("wxUser",wxUser1);
        }else {
            QueryWrapper<Company> wrapper = new QueryWrapper<>();
            wrapper.eq("id",wxUser.getCompanyId());
            Company company = companyService.getOne(wrapper);
            if (company != null){
                map.put("companyName",company.getName());
            }
            map.put("wxUser",wxUser);
        }
        map.put("token1",token);
        return map;
    }
}
