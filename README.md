# 聚合搜索

### 项目介绍

基于 Vue 3 前端 + Spring Boot 后端 + Elastic Stack 的全栈项目。

<img src="https://s11.ax1x.com/2024/02/26/pFasAT1.png" alt="pFasAT1.png" style="zoom:50%;" />

#### 界面效果图

##### 搜文章

![pFasFm9.png](https://s11.ax1x.com/2024/02/26/pFasFm9.png)

##### 搜用户

![pFasPOJ.png](https://s11.ax1x.com/2024/02/26/pFasPOJ.png)

##### 搜图片

<img src="https://s11.ax1x.com/2024/02/26/pFaskwR.png" alt="pFaskwR.png" style="zoom:50%;" />



### 前端

- Vue 3
- Node 18
- Ant Design Vue 组件库
- 页面状态同步机制

#### 运行方式：
1. 打开终端执行 `npm i` 安装所需依赖。
2. 执行 `npm run dev` 即可。

### 后端

- Spring Boot 2.7 框架
- MySQL 数据库
- ElasticSearch

#### 运行方式：

1. 更改 `application.yml` 中的数据库为本地数据库配置。
2. 在 `sql` 文件中执行初始化 sql 文件，生成表结构。
3. 下载 `ElasticSearch`（推荐 7.17.18 版本，与本项目一致）。

4. 运行 `ElasticSearch` 中 bin 目录下的 elasticsearch.bat 脚本文件。
5. 更改 `application.yml` 中的 ElasticSearch 配置信息为本地配置。
6. 启动主项目即可。
