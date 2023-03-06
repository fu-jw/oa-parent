package com.fredo.auth.service;


import com.fredo.model.system.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface SysMenuService extends IService<SysMenu> {

    /**
     * 菜单树形数据
     */
    List<SysMenu> findNodes();

}