package com.fredo.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fredo.auth.mapper.SysRoleMapper;
import com.fredo.auth.mapper.SysUserRoleMapper;
import com.fredo.auth.service.SysRoleService;
import com.fredo.model.system.SysRole;
import com.fredo.model.system.SysUserRole;
import com.fredo.vo.system.AssignRoleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    // 不用再手动注入，MP已经完成
//    @Autowired
//    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    public Map<String, Object> findRoleByAdminId(Long userId) {
        //查询所有的角色
        List<SysRole> allRolesList = this.list();

        //拥有的角色id
        List<SysUserRole> existUserRoleList = sysUserRoleMapper.selectList(
                new LambdaQueryWrapper<SysUserRole>()
                        .eq(SysUserRole::getUserId, userId)
                        .select(SysUserRole::getRoleId));
        List<Long> existRoleIdList = existUserRoleList
                .stream()
                .map(c -> c.getRoleId())
                .collect(Collectors.toList());

        //对角色进行分类
        List<SysRole> assignRoleList = new ArrayList<>();
        for (SysRole role : allRolesList) {
            //已分配
            if (existRoleIdList.contains(role.getId())) {
                assignRoleList.add(role);
            }
        }

        Map<String, Object> roleMap = new HashMap<>();
        roleMap.put("assginRoleList", assignRoleList);
        roleMap.put("allRolesList", allRolesList);
        return roleMap;
    }

    @Transactional
    @Override
    public void doAssign(AssignRoleVo assignRoleVo) {
        sysUserRoleMapper.delete(
                new LambdaQueryWrapper<SysUserRole>()
                        .eq(SysUserRole::getUserId, assignRoleVo.getUserId()));

        for (Long roleId : assignRoleVo.getRoleIdList()) {
            if (StringUtils.isEmpty(roleId)) continue;
            SysUserRole userRole = new SysUserRole();
            userRole.setUserId(assignRoleVo.getUserId());
            userRole.setRoleId(roleId);
            sysUserRoleMapper.insert(userRole);
        }
    }
}