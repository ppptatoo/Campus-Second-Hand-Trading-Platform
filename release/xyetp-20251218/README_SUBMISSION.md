# XYETP 提交运行说明（系统集成）

本包包含已编译 WAR、数据库初始化 SQL、Docker Compose 与启动脚本，可在本地一键运行。

## 目录结构

- `webapps/ROOT.war`：应用包（已配置连接到 compose 中的 MySQL）
- `sql/market.sql`：数据库初始化脚本（自动导入）
- `uploads/`：图片持久化目录（首次为空）
- `docker-compose.yml`：一键启动 MySQL + Tomcat
- `scripts/`：启动/停止/清理脚本
- `README.md`：项目总览

## 运行前准备
- 安装 Docker 与 Docker Compose（Docker Desktop 或 CLI 均可）
- 确保端口未被占用：`8080`, `3306`

## 一键启动

```bash
# 切换到本目录（release/xyetp-20251218）
./scripts/up.sh
```

启动后访问：
- 学生前台：http://localhost:8080/goods/index
- 管理后台：http://localhost:8080/admin/toLogin

### 测试账号
- 学生：15232103749 / 123456
- 管理员：17611006666 / aaa

## 一键停止

```bash
./scripts/down.sh
```

## 常见问题
- 首次启动需等待 MySQL 初始化（约 10-20 秒），Tomcat 会在 MySQL 健康检查通过后再启动。
- 如需重置 Tomcat 解压目录，可执行：

```bash
./scripts/clean.sh
```

## 说明
- 该包用于课程“系统集成”提交与验收，默认基于 Docker 本地运行。
- 线上演示地址：http://8.148.208.9:8080
