# EAD

#### 介绍
EAD (Easy Automatically Deployment)

简单自动化部署工具

www.fishin.com.cn

#### 软件架构
目前实现了将 Git 上的项目自动部署到 Web 容器。

前后端分离的开发中，后端增加一个 api，前端可以立马进行整合开发。

主要使用 Java 去调用 Git 命令下载项目，然后使用构建工具进行打包部署。

使用反射技术实现 IOC，并使用 Netty 编写了 Web 管理界面，部署情况使用 WebSocket 传输。


#### 安装教程
1. 配置
config.properties

2. 运行命令启动服务即可：
java vip.ifmm.Main

#### 使用说明
1. 配置
config.properties

2. 运行命令启动服务即可：
java vip.ifmm.Main
