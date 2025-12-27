# 校园二手交易平台(Campus-Second-Hand-Trading-Platform)

基于 Spring MVC + MyBatis 的校园二手物品交易平台，面向华南师范大学汕尾校区学生提供便捷的闲置物品交易服务。

**在线访问：** http://8.148.208.9:8080

## 目录
- [项目简介](#项目简介)
- [核心功能](#核心功能)
- [技术栈](#技术栈)
- [在线演示](#在线演示)
- [本地开发](#本地开发)
- [部署架构](#部署架构)
- [项目成员](#项目成员)

---

## 项目简介

校园二手交易平台（Campus-Second-Hand-Trading-Platform）是一个面向大学生群体的在线二手物品交易系统，提供完整的商品发布、浏览、交易、管理功能。系统采用前后台分离设计，学生用户可以发布和购买闲置物品，管理员可以对平台进行全面管理和维护。

### 核心特性
- 💼 **商品管理**：支持图片上传、分类管理、商品搜索
- 🛒 **交易流程**：在线下单、订单管理、模拟支付
- 💬 **互动功能**：商品留言、想要收藏、举报机制
- 📊 **后台管理**：商品审核、用户管理、数据统计
- 🔐 **权限隔离**：学生账号与管理员账号完全分离，权限分级管理
- 📦 **持久化存储**：图片文件永久保存，容器重启数据不丢失

---

## 核心功能

### 学生端功能
- **商品浏览**
  - 首页展示最新上架商品
  - 按分类筛选浏览
  - 关键词搜索商品
  
- **发布管理**
  - 发布闲置商品（支持最多5张图片）
  - 设置交易方式（在线/线下/两者皆可）
  - 管理已发布商品（擦亮、下架）
  
- **交易功能**
  - 在线下单购买
  - 订单确认与支付
  - 查看购买/出售记录
  
- **个人中心**
  - 个人信息管理
  - 收货地址管理
  - 收藏的商品
  - 收入/支出统计

### 管理员后台
- **用户管理**：查看用户列表、调整权限等级
- **商品管理**：审核/下架商品、查看商品详情
- **订单管理**：查看平台所有订单记录
- **分类管理**：添加/编辑/删除商品分类
- **举报处理**：处理用户举报信息
- **评论管理**：管理商品留言

---

## 技术栈

### 后端
- **框架**：Spring MVC 5.3.39 + MyBatis 3.2.8
- **语言**：Java 11
- **数据库**：MySQL 8.0
- **连接池**：Druid 1.1.10
- **构建工具**：Maven 3.9+
- **容器**：Tomcat 9.0 (Docker)

### 前端
- **UI框架**：Layui
- **模板引擎**：JSP
- **样式**：CSS3 + Bootstrap
- **脚本**：jQuery

### 部署环境
- **服务器**：阿里云 ECS (CentOS 8.5)
- **容器化**：Docker
- **反向代理**：无（直接暴露 8080 端口）
- **持久化存储**：宿主机挂载卷

---

## 在线演示

**平台地址：** http://8.148.208.9:8080

### 测试账号

| 角色 | 手机号 | 密码 | 说明 |
|------|--------|------|------|
| 学生用户 | `15232103749` | `123456` | 可发布商品、购买、留言等 |
| 管理员 | `17611006666` | `aaa` | 后台管理权限 |

### 访问入口
- **学生前台**：http://8.148.208.9:8080/goods/index
- **管理后台**：http://8.148.208.9:8080/admin/toLogin

---

## 本地开发

### 环境要求
- JDK 11+
- Maven 3.6+
- MySQL 5.7+ / 8.0+
- IDEA / Eclipse

### 快速启动

1. **克隆项目**
   ```bash
   git clone https://github.com/lululinran/xyetp.git
   cd xyetp
   ```

2. **数据库初始化**
   - 创建数据库 `market`
   - 导入 SQL 文件：`src/main/resources/market.sql`

3. **配置数据库连接**
   
   编辑 `src/main/resources/jdbc.properties`：
   ```properties
   url=jdbc:mysql://localhost:3306/market?useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true&serverTimezone=UTC
   username=root
   password=你的密码
   ```

4. **Maven 构建**
   ```bash
   mvn clean package
   ```

5. **运行项目**
   
   **方式一：IDEA 内置 Tomcat**
   - Run → Edit Configurations → 添加 Tomcat Server
   - Deployment 添加 `kd-second-hand-workshop:war exploded`
   - Application context 设为 `/`
   - 端口设为 `8080`
   - 启动访问 http://localhost:8080/goods/index

   **方式二：外部 Tomcat**
   - 将 `target/kd-second-hand-workshop.war` 复制到 Tomcat 的 `webapps/` 目录
   - 重命名为 `ROOT.war`
   - 启动 Tomcat，访问 http://localhost:8080/goods/index

---

## 部署架构

### 云端部署拓扑

```
阿里云 ECS (CentOS 8.5)
├── Docker Engine
│   ├── kd-app (Tomcat 9 容器)
│   │   ├── 端口映射: 8080:8080
│   │   ├── 卷挂载: /root/tomcat/webapps -> /usr/local/tomcat/webapps
│   │   └── 卷挂载: /root/uploads -> /data/uploads (持久化图片存储)
│   └── mysql (MySQL 8 容器)
│       ├── 端口映射: 3306:3306
│       └── 数据库: market
└── 宿主机目录
    ├── /root/tomcat/webapps/ROOT (应用部署目录)
    └── /root/uploads/web (图片持久化目录)
```

### 关键配置

**静态资源映射**（Spring 配置）：
```xml
<mvc:resources mapping="/files/web/**" location="file:/data/uploads/web/" />
```

**图片上传路径**（PublishController）：
```java
File uploadFile = new File("/data/uploads/web/");
```

**权限拦截器**：
- `AccessInterceptor`：拦截 `/user/**`，仅允许学生账号（power ≤ 50）
- `AdminAccessInterceptor`：拦截 `/admin/**`，仅允许管理员账号（power > 50）

---

## 项目成员

**23大数据1班**
- 陆林然
- 曾子怡
- 黄旭琪
- 虞小珊
- 王思琦

---

## License

MIT License

Copyright © 2025 华南师范大学汕尾校区 23大数据1班
