package com.yipin_server.yihuo.config;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yipin_server.yihuo.entity.User;
import com.yipin_server.yihuo.entity.WxUser;
import com.yipin_server.yihuo.exception.ServiceException;
import com.yipin_server.yihuo.service.AdminService;
import com.yipin_server.yihuo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private AdminService adminService;

    @Autowired
    private IUserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader("token");
        String token1 = request.getHeader("token1");
        // 如果不是映射到方法直接通过
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod handlerMethod=(HandlerMethod)handler;
        Method method=handlerMethod.getMethod();
        //跳过认证
        if (method.isAnnotationPresent(AuthAccess.class)) {
            AuthAccess authAccess = method.getAnnotation(AuthAccess.class);
            if (authAccess.required()) {
                return true;
            }
            return true;
        }
        System.out.println("token1-------------"+token1);
        if (StrUtil.isBlank(token)&&StrUtil.isBlank(token1)) {
            throw new ServiceException(Constants.CODE_401, "无token，");
        }
        // 获取 token 中的 user id
        String userId = null,openId = null;
        System.out.println("111111111111111111111111111111111111");
//        try {
//            userId = JWT.decode(token).getAudience().get(0);
//            wxUserId = JWT.decode(token1).getAudience().get(0);
//        } catch (JWTDecodeException j) {
//            throw new ServiceException(Constants.CODE_401, "token获取值失败，");
//        }
        User user = null;
        WxUser wxUser = null;
        if (StrUtil.isNotBlank(token)){
            userId = JWT.decode(token).getAudience().get(0);
            user = adminService.getById(userId);
            System.out.println("3333333333333333333333333333");
            return true;
        }else {
            openId = JWT.decode(token1).getAudience().get(0);
            QueryWrapper<WxUser> wrapper = new QueryWrapper<>();
            wrapper.eq("open_id",openId);
            wxUser = userService.getOne(wrapper);
            System.out.println("3333333333333333333333333333");
            return true;
        }
//        // 根据token中的userid查询数据库
//        System.out.println("2222222222222222222222222222");
//        if (user != null ) {
//            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
//            jwtVerifier.verify(token); // 验证token
//        }else if (wxUser != null) {
//            JWTVerifier jwtVerifier1 = JWT.require(Algorithm.HMAC256(wxUser.getOpenId())).build();
////            String[] tokenString = token1.split(" ");
//            jwtVerifier1.verify(token1); // 验证token
//        }else{
//            throw new ServiceException(Constants.CODE_401, "用户不存在，请重新登录");
//        }

    }
}
