package com.fredo.auth.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fredo.auth.service.SysRoleService;
import com.fredo.common.result.Result;
import com.fredo.model.system.SysRole;
import com.fredo.vo.system.SysRoleQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "角色管理")
@RestController
@RequestMapping("/admin/system/sysRole")
public class SysRoleController {

    @Autowired
    private SysRoleService roleService;

    @ApiOperation("获取全部角色列表")
    @GetMapping("findAll")
    public Result<List<SysRole>> findAll() {
        List<SysRole> roleList = roleService.list();
        // 测试异常处理
//        try {
//            int a=1/0;
//        }catch (Exception e){
//            e.printStackTrace();
//            throw new CustomException(ResultCodeEnum.SERVICE_ERROR.getCode(),
//                    ResultCodeEnum.SERVICE_ERROR.getMessage());
//        }
        return Result.ok(roleList);
    }

    /**
     * 条件分页查询
     *
     * @param page           当前页
     * @param limit          每页显示记录数
     * @param sysRoleQueryVo 条件对象
     * @return 条件分页查询结果
     */
    @ApiOperation("角色条件分页查询")
    @GetMapping("{page}/{limit}")
    public Result pageQueryRole(@PathVariable Long page,
                                @PathVariable Long limit,
                                SysRoleQueryVo sysRoleQueryVo) {
        //调用service的方法实现
        //1 创建Page对象，传递分页相关参数
        //page 当前页  limit 每页显示记录数
        Page<SysRole> pageParam = new Page<>(page, limit);

        //2 封装条件，判断条件是否为空，不为空进行封装
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        String roleName = sysRoleQueryVo.getRoleName();
        if (!StringUtils.isEmpty(roleName)) {
            //封装 like模糊查询
            wrapper.like(SysRole::getRoleName, roleName);
        }

        //3 调用方法实现，以下三种写法都可以
//        IPage<SysRole> pageModel = sysRoleService.page(pageParam, wrapper);
//        return Result.ok(pageModel);

//        pageParam = sysRoleService.page(pageParam, wrapper);
//        return Result.ok(pageParam);

        roleService.page(pageParam, wrapper);
        return Result.ok(pageParam);
    }

    /**
     * 根据id获取角色信息
     *
     * @param id id
     * @return 角色信息
     */
    @ApiOperation(value = "根据id获取角色信息")
    @GetMapping("get/{id}")
    public Result<SysRole> get(@PathVariable Long id) {
        SysRole role = roleService.getById(id);
        return Result.ok(role);
    }

    /**
     * 新增角色
     *
     * @param role 新增的角色信息
     * @return 新增结果
     */
    @ApiOperation(value = "新增角色")
    @PostMapping("save")
    public Result save(@RequestBody @Validated SysRole role) {
        roleService.save(role);
        return Result.ok();
    }

    /**
     * 修改角色
     *
     * @param role 角色
     * @return 修改结果
     */
    @ApiOperation(value = "修改角色")
    @PutMapping("update")
    public Result update(@RequestBody SysRole role) {
        roleService.updateById(role);
        return Result.ok();
    }

    /**
     * 删除角色
     *
     * @param id id
     * @return 删除结果
     */
    @ApiOperation(value = "删除角色")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id) {
        roleService.removeById(id);
        return Result.ok();
    }

    /**
     * 根据id列表删除角色
     *
     * @param idList id列表
     * @return 删除结果
     */
    @ApiOperation(value = "根据id列表删除")
    @DeleteMapping("batchRemove")
    public Result batchRemove(@RequestBody List<Long> idList) {
        roleService.removeByIds(idList);
        return Result.ok();
    }
}
