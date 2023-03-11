package com.fredo.auth.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fredo.auth.service.SysMenuService;
import com.fredo.auth.service.SysUserService;
import com.fredo.common.util.MD5;
import com.fredo.common.execption.CustomException;
import com.fredo.common.jwt.JwtHelper;
import com.fredo.common.result.Result;
import com.fredo.model.system.SysUser;
import com.fredo.vo.system.LoginVo;
import com.fredo.vo.system.RouterVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

@Api(tags = "后台管理")
@RestController
@RequestMapping("/admin/system/index")
public class IndexController {

    @Autowired
    private SysUserService userService;
    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 登录
     * /admin/system/index/login
     * post
     * 添加权限后的登录步骤：
     *  1 获取登录时的name和id
     *  2 根据name查数据库
     *  3 判断用户是否存在
     *  4 判断密码是否正确
     *  5 判断用户状态是否禁用
     *  6 根据id和name生成token
     *  7 返回
     */
    @ApiOperation("登录")
    @PostMapping("login")
    public Result login(@RequestBody LoginVo loginVo){
//        System.out.println(map);
//        Map<String, Object> map = new HashMap<>();
//        map.put("token",map.get("username"));
//        return Result.ok(map);

        // 1 获取登录时的name和id
        String loginName = loginVo.getUsername();
        // 2 根据name查数据库
        // Map<String, Object> userInfo = userService.getUserInfo(loginName);
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getUsername,loginName);
        SysUser sysUser = userService.getOne(wrapper);
        // 3 判断用户是否存在
        if (StringUtils.isEmpty(sysUser)) {
            throw new CustomException(201,"用户名错误");
        }
        // 4 判断密码是否正确
        if (!sysUser.getPassword().equals(MD5.encrypt(loginVo.getPassword()))) {
            throw new CustomException(201,"密码错误");
        }
        // 5 判断用户状态是否禁用
        if (1 != sysUser.getStatus()) {
            throw new CustomException(201,"用户禁止登录");
        }
        // 6 根据id和name生成token
        String token = JwtHelper.createToken(sysUser.getId(), sysUser.getUsername());
        // 7 返回
        Map<String, String> map = new HashMap<>();
        map.put("token",token);
        return Result.ok(map);
    }

    /**
     * 获取用户信息
     *  包含：当前登录用户的菜单权限及按钮权限
     *  1 从请求头获取token
     *  2 从token获取id或name
     *  3 根据id或name查数据库获取用户信息
     *  4 根据用户id获取可操作菜单列表
     *      查数据库动态构建路由规则并显示
     *  5 根据用户id获取可操作按钮列表
     *  6 返回
     */
    @ApiOperation("获取用户信息")
    @GetMapping("info")
    public Result info(HttpServletRequest request) {
//        Map<String, Object> map = new HashMap<>();
//        map.put("roles","[admin]");
//        map.put("name","admin");
//        map.put("avatar","https://oss.aliyuncs.com/aliyun_id_photo_bucket/default_handsome.jpg");
//        return Result.ok(map);

        // 1 从请求头获取token
        String token = request.getHeader("token");
        // 2 从token获取id或name
        Long userId = JwtHelper.getUserId(token);
        // 3 根据id或name查数据库获取用户信息
        SysUser sysUser = userService.getById(userId);
        // 4 根据用户id获取可操作菜单列表
        List<RouterVo> routerVoList = sysMenuService.findUserMenuListByUserId(userId);
        // 5 根据用户id获取可操作按钮列表
        List<String> permsList = sysMenuService.findUserPermsList(userId);

        Map<String, Object> result = new HashMap<>();
        result.put("name", sysUser.getName());
        result.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        //当前权限控制使用不到，我们暂时忽略
        result.put("roles",  new HashSet<>());
        result.put("buttons", permsList);
        result.put("routers", routerVoList);
        return Result.ok(result);
    }

    /**
     * 登出
     */
    @ApiOperation("登出")
    @PostMapping("logout")
    public Result logout(){
        return Result.ok();
    }
}
