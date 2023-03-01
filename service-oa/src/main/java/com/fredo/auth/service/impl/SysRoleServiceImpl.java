package com.fredo.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fredo.auth.mapper.SysRoleMapper;
import com.fredo.auth.service.SysRoleService;
import com.fredo.model.system.SysRole;
import org.springframework.stereotype.Service;

@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    // 不用再手动注入，MP已经完成
//    @Autowired
//    private SysRoleMapper sysRoleMapper;

}