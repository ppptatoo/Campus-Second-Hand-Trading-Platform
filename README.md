# 科大二手工坊

基于 Spring MVC + Spring + MyBatis 的校园二手物品交易平台（前台 + 后台）。当前分支正在升级到 Java 21 与 Spring Framework 6，以适配 Jakarta API 与最新 LTS 运行环境。

> 配置讲解视频： [科大二手工坊项目配置教程（B站）](https://b23.tv/RPuICwZ)

## 快速索引
- [项目概览](#项目概览)
- [技术栈与要求](#技术栈与要求)
- [快速开始（本地）](#快速开始本地)
- [账号信息](#账号信息)
- [常见问题](#常见问题)
- [功能列表](#功能列表)
- [更新记录](#更新记录)

## 项目概览
- 二手物品列表/搜索/分类浏览
- 商品发布、图片上传、留言与收藏
- 订单流转与模拟支付
- 地址管理与个人中心
- 后台：商品、分类、订单、举报/轮播等管理

## 技术栈与要求
- Java 21（LTS）
- Spring Framework 6.x（Jakarta 命名空间）
- MyBatis 3.x
- MySQL 5.7+/8.0+
- Maven 3.9+
- Servlet 6 / Tomcat 10.1+（推荐，Jakarta API）

## 快速开始（本地）
1) 克隆代码并导入 IDEA（确保 Maven 自动导入）。
2) 数据库：创建 `market` 库并导入 `src/main/resources/market.sql`。
3) 配置 `src/main/resources/jdbc.properties`：更新 `username/password` 及 `url`（保持 `useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true&serverTimezone=UTC`）。
4) 构建
```bash
mvn clean package
```
5) 运行（两种方式）
   - IDEA 配置 Tomcat 10.1+，部署 `kd-second-hand-workshop:war exploded`，Context Path `/`，端口 `8088`
   - 或将 `target/kd-second-hand-workshop.war` 放入 Tomcat `webapps/`，启动后访问

访问地址：
- 前台：`http://localhost:8088/goods/index`
- 后台：`http://localhost:8088/admin/toLogin`

## 便携包运行（课堂演示推荐）
- 已添加 Maven Wrapper（锁 Maven 3.9.9）与 Jetty 插件；无需外部 Tomcat。
- 可在根目录放置便携 JDK 到 `./jre/`（可选）。脚本会优先使用这里的 JDK，然后才用 `JAVA_HOME` 或系统 java。
- 默认端口 8080，可通过环境变量 `PORT` 覆盖。

使用：
- macOS/Linux：
   ```bash
   chmod +x run.sh
   PORT=8080 ./run.sh   # 若不设 PORT 默认为 8080
   ```
- Windows：双击 `run.bat`，或在 CMD 里执行：
   ```bat
   set PORT=8080 && run.bat
   ```

启动后访问：
- 前台：`http://localhost:8080/goods/index`
- 后台：`http://localhost:8080/admin/toLogin`

## 账号信息
- 学生：`15232103749 / 123456`
- 管理员：`17611006666 / aaa`

## 常见问题
- **启动 404**：确认访问路径 `/goods/index`，检查 web.xml 映射和 Tomcat 端口。
- **数据库连接失败**：核对 `jdbc.properties`，确认 MySQL 运行且 `market` 已导入。
- **图片不显示/上传失败**：确保上传目录具备写权限，使用 Tomcat 10.1+ 并保持 UTF-8 配置。
- **Jakarta 兼容性**：确保使用 Tomcat 10.1+ 或兼容 Servlet 6 容器；旧版 Tomcat 8/9 的 `javax.*` API 不再适配。

## 功能列表
前台：
- 分类浏览、搜索、发布/管理闲置、留言、收藏
- 订单创建/确认/模拟支付，个人中心，地址管理，收入/支出统计

后台：
- 商品、分类、订单、举报、留言、轮播管理

## 更新记录（节选）
- 2025-12：开始升级到 Java 21 / Spring 6，适配 Jakarta API 与新运行环境。
- 2022-02：修复头像显示与订单确认 404 问题。
- 2021-12：修复上传路径与累计收入问题，清理测试数据。
- 2021-01：统一项目根路径。
- 2020-12：升级到 Spring 5.x，补充 gitignore。
- 2020-04：补充 SQL 文件，保障可启动。
