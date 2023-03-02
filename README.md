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
1. 获取所有角色：/admin/system/sysRole/findAll
2. 角色条件分页查询
3. 

## 五、项目亮点
1. 统一返回结果
2. 整合knife4j，集成Swagger生成Api文档的增强解决方案
3. 条件分页查询
