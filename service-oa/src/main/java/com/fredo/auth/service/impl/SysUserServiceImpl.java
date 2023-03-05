package com.fredo.auth.service.impl;

import com.fredo.model.system.SysUser;
import com.fredo.auth.mapper.SysUserMapper;
import com.fredo.auth.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

}
