package com.fredo.auth.controller;

import com.fredo.common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Api(tags = "后台管理")
@RestController
@RequestMapping("/admin/system/index")
public class IndexController {

    /**
     * 登录
     * /admin/system/index/login
     * post
     */
    @ApiOperation("登录")
    @PostMapping("login")
    public Result login(@RequestBody HashMap map){
        System.out.println(map);
//        Map<String, Object> map = new HashMap<>();
        map.put("token",map.get("username"));
        return Result.ok(map);
    }

    /**
     * 获取用户信息
     */
    @ApiOperation("获取用户信息")
    @GetMapping("info")
    public Result info() {
        Map<String, Object> map = new HashMap<>();
        map.put("roles","[admin]");
        map.put("name","admin");
        map.put("avatar","https://oss.aliyuncs.com/aliyun_id_photo_bucket/default_handsome.jpg");
        return Result.ok(map);
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
