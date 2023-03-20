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
28. 

## 五、项目亮点
1. 统一返回结果
2. 整合knife4j，集成Swagger生成Api文档的增强解决方案
3. 条件分页查询
4. 统一异常处理
5. SpringSecurity 用户认证授权

## 六、SpringSecurity 
Spring Security 基于 Spring 框架，提供了一套 Web 应用安全性的完整解决方案
Web 应用的安全性包括 **用户认证（Authentication）和用户授权（Authorization）** 两个部分，
这两点也是 SpringSecurity 重要核心功能

- 用户认证：验证某个用户是否为系统中的合法主体，也就是说用户能否访问该系统。用户认证一般要求用户提供用户名和密码，系统通过校验用户名和密码来完成认证过程
- 用户授权：验证某个用户是否有权限执行某个操作

```txt
要对 Web 资源进行保护，最好的办法莫过于 Filter
要想对方法调用进行保护，最好的办法莫过于 AOP
```
Spring Security 进行认证和鉴权的时候，就是利用的一系列的 Filter 来进行拦截

### 认证流程
0. 在登录页输入用户名和密码，提交登录请求
1. 将用户名和密码封装到 Authentication(接口，实现类是UsernamePasswordAuthenticationToken)
2. 由 AuthenticationManager 认证 authentication(),并将结果封装到 Authentication 中
3. 认证结果放到上下文中

```java
// 生成一个包含账号密码的认证信息
Authentication authenticationToken = new UsernamePasswordAuthenticationToken(username, passwrod);
// AuthenticationManager校验这个认证信息，返回一个已认证的Authentication
Authentication authentication = authenticationManager.authenticate(authenticationToken);
// 将返回的Authentication存到上下文中
SecurityContextHolder.getContext().setAuthentication(authentication);
```
AuthenticationManager 校验逻辑的大概源码:
```java
public Authentication authenticate(Authentication authentication) throws AuthenticationException {
...省略其他代码

    // 传递过来的用户名
    String username = authentication.getName();
    // 调用UserDetailService的方法，通过用户名查询出用户对象UserDetail（查询不出来UserDetailService则会抛出异常）
    UserDetails userDetails = this.getUserDetailsService().loadUserByUsername(username);
    String presentedPassword = authentication.getCredentials().toString();

    // 传递过来的密码
    String password = authentication.getCredentials().toString();
    // 使用密码解析器PasswordEncoder传递过来的密码是否和真实的用户密码匹配
    if (!passwordEncoder.matches(password, userDetails.getPassword())) {
        // 密码错误则抛出异常
        throw new BadCredentialsException("错误信息...");
    }

    // 注意哦，这里返回的已认证Authentication，是将整个UserDetails放进去充当Principal
    UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(userDetails,
            authentication.getCredentials(), userDetails.getAuthorities());
    return result;

...省略其他代码
}
```
具体实现：
1、自定义 UserDetails 
接口，SpringSecurity 提供了实现类 User
`public class User implements UserDetails, CredentialsContainer {}`
需要自定义User类继承SpringSecurity 提供了实现类 User，并包含系统中使用的 SysUser：
```java
public class CustomUser extends User {
    /**
     * 我们自己的用户实体对象，要调取用户信息时直接获取这个实体对象。（这里我就不写get/set方法了）
     */
    private SysUser sysUser;
    public CustomUser(SysUser sysUser, Collection<? extends GrantedAuthority> authorities) {
        super(sysUser.getUsername(), sysUser.getPassword(), authorities);
        this.sysUser = sysUser;
    }
    public SysUser getSysUser() {return sysUser;}
    public void setSysUser(SysUser sysUser) {this.sysUser = sysUser;}
}
```
2、实现业务对象 UserDetailsService
接口，
```java
public interface UserDetailsService {
    /**
     * 根据用户名获取用户对象（获取不到直接抛异常）
     */
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
```
添加UserDetailsServiceImpl类，实现UserDetailsService接口
```java
@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private SysUserService sysUserService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = sysUserService.getByUsername(username);
        if(null == sysUser) {
            throw new UsernameNotFoundException("用户名不存在！");}
        if(sysUser.getStatus().intValue() == 0) {
            throw new RuntimeException("账号已停用");}
        return new CustomUser(sysUser, Collections.emptyList());
    }
}
```
// TODO 还需要再整理



## 其他
### 关于跨域处理
请求协议+IP+端口号，三者任一不同，就属于跨域

解决方案如下：
1、在前端处理：
在 vue.config.js 文件中设置代理
```js
// 将 ‘/dev-api’ 开头的请求改到 http://localhost:8800/...
proxy: {
  '/dev-api': { // 匹配所有以 '/dev-api'开头的请求路径
	target: 'http://localhost:8800',
	changeOrigin: true, // 支持跨域
	pathRewrite: { // 重写路径: 去掉路径中开头的'/dev-api'
	  '^/dev-api': ''
	}
  }
}
```
2、在后端处理
```java
// 直接在 Controller 层添加注解
@CrossOrigin

```

### xml文件加载问题

Maven默认情况下，在src - main -java目录下面，只会加载java类型文件，其他类型文件不会加载的

- 第一种解决方式：把xml文件放到resources目录下 
- 第二种解决方式：在pom.xml和项目配置文件进行配置
```xml
<build>
	// ...
	<resources>
		<resource>
			<directory>src/main/java</directory>
			<includes>
				<include>**/*.yml</include>
				<include>**/*.properties</include>
				<include>**/*.xml</include>
			</includes>
			<filtering>false</filtering>
		</resource>
		<resource>
			<directory>src/main/resources</directory>
			<includes> 
				<include>**/*.yml</include>
				<include>**/*.properties</include>
				<include>**/*.xml</include>
				<include>**/*.png</include>
				<include>**/*.zip</include>
			</includes>
			<filtering>false</filtering>
		</resource>
	</resources>
</build>
```
### mapper扫描问题

```java
//第一种方式 ：创建配置类，使用@MapperScan注解
@Configuration
@MapperScan(basePackages = {"com.fredo.*.mapper"})
public class MybatisPlusConfig {

}

//第二种方式：在mapper的接口上面添加注解 @Mapper
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {
    
}
```



