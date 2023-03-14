package com.fredo.filter;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fredo.common.jwt.JwtHelper;
import com.fredo.common.result.Result;
import com.fredo.common.result.ResultCodeEnum;
import com.fredo.common.util.ResponseUtil;
import com.fredo.custom.CustomUser;
import com.fredo.vo.system.LoginVo;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TokenLoginFilter extends UsernamePasswordAuthenticationFilter {

    private RedisTemplate redisTemplate;

    // 构造方法
    public TokenLoginFilter(AuthenticationManager manager, RedisTemplate redisTemplate) {

        this.redisTemplate = redisTemplate;
        this.setAuthenticationManager(manager);
        this.setPostOnly(false);
        //指定登录接口及提交方式，可以指定任意路径
        this.setRequiresAuthenticationRequestMatcher(
                new AntPathRequestMatcher(
                        "/admin/system/index/login", "POST"));
    }

    // 登录认证--获取登录的用户名密码，调用认证方法
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response)
            throws AuthenticationException {

        try {
            // 获取登录的用户名密码
            LoginVo loginVo = new ObjectMapper().readValue(request.getInputStream(), LoginVo.class);
            // 封装用户名和密码生成到 Authentication
            Authentication authenticationToken = new UsernamePasswordAuthenticationToken(loginVo.getUsername(), loginVo.getPassword());
            // 认证并返回结果
            return this.getAuthenticationManager().authenticate(authenticationToken);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // 认证成功回调
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult)
            throws IOException, ServletException {
        // 获取当前用户
        CustomUser customUser = (CustomUser) authResult.getPrincipal();
        // 生成 token
        String token = JwtHelper.createToken(customUser.getSysUser().getId(), customUser.getSysUser().getUsername());
        // 获取当前用户权限数据，放到Redis中
        // K：username   V：权限数据
        redisTemplate.opsForValue().set(
                customUser.getUsername(), JSON.toJSONString(customUser.getAuthorities()));

        // 封装结果
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        // 原生方式返回
        ResponseUtil.out(response, Result.ok(map));
    }

    // 认证失败回调
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException failed)
            throws IOException, ServletException {

        if (failed.getCause() instanceof RuntimeException) {
            ResponseUtil.out(response, Result.build(null, 204, failed.getMessage()));
        } else {
            ResponseUtil.out(response, Result.build(null, ResultCodeEnum.LOGIN_ERROR));
        }
    }
}
