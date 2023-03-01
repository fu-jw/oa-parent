package com.fredo.auth;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fredo.auth.mapper.SysRoleMapper;
import com.fredo.model.system.SysRole;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * 增删改查测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SysRoleMapperTest {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    // 查询操作
    @Test
    public void testSelectList() {
        System.out.println(("----- selectAll method test ------"));
        //UserMapper 中的 selectList() 方法的参数为 MP 内置的条件封装器 Wrapper
        //所以不填写就是无任何条件
        List<SysRole> users = sysRoleMapper.selectList(null);
        users.forEach(System.out::println);
    }

    // 插入操作
    @Test
    public void testInsert(){
        SysRole sysRole = new SysRole();
        sysRole.setRoleName("测试");
        sysRole.setRoleCode("test");
        sysRole.setDescription("test角色管理员");

        int result = sysRoleMapper.insert(sysRole);
        System.out.println(result); //影响的行数
        System.out.println(sysRole); //id自动回填
    }

    // 更新操作
    @Test
    public void testUpdate(){
//        SysRole role = new SysRole();
//        role.setId(1L);
        SysRole role = sysRoleMapper.selectById(1L);
        role.setRoleName("系统管理员");

        int update = sysRoleMapper.updateById(role);
        System.out.println(update);
    }

    // 删除操作
    @Test
    public void testDelete(){
        // deleteById
        int delete = sysRoleMapper.deleteById(1);

//        SysRole role = new SysRole();
//        role.setId(11L);
//        int delete = sysRoleMapper.deleteById(role);
//        System.out.println(delete);

        // delete----QueryWrapper
//        QueryWrapper<SysRole> wrapper = new QueryWrapper<>();
//        wrapper.eq("id", 11L);
//        sysRoleMapper.delete(wrapper);

        // delete----LambdaQueryWrapper
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysRole::getId,1L);
        sysRoleMapper.delete(wrapper);
    }
}