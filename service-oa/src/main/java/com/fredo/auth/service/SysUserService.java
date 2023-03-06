package com.fredo.auth.service;

import com.fredo.model.system.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 */
public interface SysUserService extends IService<SysUser> {

    void updateStatus(Long id, Integer status);
}
