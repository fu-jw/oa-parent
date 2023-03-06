package com.fredo.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fredo.auth.mapper.SysRoleMapper;
import com.fredo.auth.service.SysRoleService;
import com.fredo.model.system.SysRole;
import com.fredo.vo.system.AssignRoleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    // 不用再手动注入，MP已经完成
//    @Autowired
//    private SysRoleMapper sysRoleMapper;
    @Autowired
//    private SysUserRoleMapper sysUserRoleMapper;
    @Override
    public Map<String, Object> findRoleByAdminId(Long userId) {
        return null;
    }

    @Override
    public void doAssign(AssignRoleVo assignRoleVo) {

    }



}