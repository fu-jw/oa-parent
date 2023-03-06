package com.fredo.auth.service;


import com.fredo.model.system.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fredo.vo.system.AssignMenuVo;

import java.util.List;

public interface SysMenuService extends IService<SysMenu> {

    /**
     * 菜单树形数据
     */
    List<SysMenu> findNodes();

    /**
     * 根据角色获取授权权限数据
     * @return
     */
    List<SysMenu> findSysMenuByRoleId(Long roleId);

    /**
     * 保存角色权限
     * @param  assignMenuVo
     */
    void doAssign(AssignMenuVo assignMenuVo);
}