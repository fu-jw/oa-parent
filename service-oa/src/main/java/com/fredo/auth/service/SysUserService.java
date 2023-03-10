package com.fredo.auth.service;

import com.fredo.model.system.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fredo.vo.system.RouterVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表 服务类
 * </p>
 */
public interface SysUserService extends IService<SysUser> {

    // 更新用户状态
    void updateStatus(Long id, Integer status);

    // 根据用户名获取用户信息
    SysUser getByUsername(String username);

    // 获取用户菜单
//    List<RouterVo> findUserMenuList(Long userId);

    // 根据用户名获取用户登录信息
    // Map<String, Object> getUserInfo(String username);
}
