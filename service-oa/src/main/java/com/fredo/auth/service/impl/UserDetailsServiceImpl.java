package com.fredo.auth.service.impl;

import com.fredo.auth.service.SysMenuService;
import com.fredo.auth.service.SysUserService;
import com.fredo.common.execption.CustomException;
import com.fredo.common.result.ResultCodeEnum;
import com.fredo.custom.CustomUser;
import com.fredo.model.system.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    SysMenuService sysMenuService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = sysUserService.getByUsername(username);
        if (null == sysUser) {
            throw new UsernameNotFoundException("用户名不存在！");
        }

//        if (sysUser.getStatus().intValue() == 0) {
//            throw new RuntimeException("账号已停用");
//        }
//        return new CustomUser(sysUser, Collections.emptyList());

        if (sysUser.getStatus().intValue() == 0) {
            throw new CustomException(ResultCodeEnum.ACCOUNT_STOP);
        }
        // 根据用户id查询操作权限列表
        List<String> userPermsList = sysMenuService.findUserPermsList(sysUser.getId());
        // 封装权限数据
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (String perm : userPermsList) {
            authorities.add(new SimpleGrantedAuthority(perm.trim()));
        }
        
        return new CustomUser(sysUser, authorities);
    }
}