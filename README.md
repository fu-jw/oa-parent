# OA工程
## 一、项目概述
办公系统是一套自动办公系统，系统主要包含：管理端和员工端

管理端包含：权限管理、审批管理、公众号菜单管理

员工端采用微信公众号操作，包含：办公审批、微信授权登录、消息推送等功能

项目服务器端架构：SpringBoot + MyBatisPlus + SpringSecurity + Redis + Activiti+ MySQL

前端架构：vue-admin-template + Node.js + Npm + Vue + ElementUI + Axios

## 二、核心技术
| 基础框架：SpringBoot                                         |
| ------------------------------------------------------------ |
| 数据缓存：Redis                                              |
| 数据库：MySQL                                                |
| 权限控制：SpringSecurity                                     |
| 工作流引擎：Activiti                                         |
| 前端技术：vue-admin-template + Node.js + Npm + Vue + ElementUI + Axios |
| 微信公众号：公众号菜单 + 微信授权登录 + 消息推送             |

## 三、项目模块
- oa-parent：根目录，管理子模块：
	- common：公共类父模块
		- common-util：核心工具类
		- service-util：service模块工具类
		- spring-security：spring-security业务模块
	- model：实体类模块
	- service-oa：系统服务模块

## 四、接口汇总
1. 角色管理--获取所有角色：/admin/system/sysRole/findAll
2. 角色管理--分页查询：/admin/system/sysRole/{page}/{limit}
3. 角色管理--根据id获取角色信息：/admin/system/sysRole/get/{id}
4. 角色管理--新增角色：/admin/system/sysRole/save
5. 角色管理--修改角色:/admin/system/sysRole/update
6. 角色管理--删除角色:/admin/system/sysRole/remove/{id}
7. 角色管理--根据id列表删除角色:/admin/system/sysRole/batchRemove
8. 后台管理--登录：/admin/system/index/login
9. 后台管理--获取信息：/admin/system/index/info
10. 后台管理--登出：/admin/system/index/logout
11. 用户管理--分页查询：/admin/system/sysUser/{page}/{limit}
12. 用户管理--根据id获取用户信息：/admin/system/sysUser/get/{id}
13. 用户管理--保存用户信息：/admin/system/sysUser/save
14. 用户管理--更新用户信息：/admin/system/sysUser/update
15. 用户管理--根据id删除用户信息：/admin/system/sysUser/remove/{id}
16. 用户管理--批量删除用户信息：/admin/system/sysUser/batchRemove
17. 用户管理--获取用户的角色信息：/admin/system/sysUser/toAssign/{userId}
18. 用户管理--给用户分配角色：/admin/system/sysUser/doAssign
19. 用户管理--更新用户状态：/admin/system/sysUser/updateStatus/{id}/{status}
20. 角色管理--根据用户获取角色数据：/admin/system/sysRole/toAssign/{userId}
21. 角色管理--分配角色：/admin/system/sysRole/doAssign
22. 菜单管理--获取所有菜单：/admin/system/sysMenu/findNodes
23. 菜单管理--新增菜单：/admin/system/sysMenu/save
24. 菜单管理--修改菜单：/admin/system/sysMenu/update
25. 菜单管理--删除菜单：/admin/system/sysMenu/remove/{id}
26. 菜单管理--根据角色获取菜单：/admin/system/sysMenu/toAssign/{roleId}
27. 菜单管理--给角色分配权限：/admin/system/sysMenu/doAssign

## 五、项目亮点
1. 统一返回结果
2. 整合knife4j，集成Swagger生成Api文档的增强解决方案
3. 条件分页查询
4. 统一异常处理


