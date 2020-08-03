# SpringCloud 演示 Demo

## 选型说明
- 配置中心：Spring Cloud Config
- 注册中心：Eureka
- 服务发现：Spring DiscoveryClient/带有Ribbon功能的Spring RestTemplate/Feign

## 基础

### configuration-server(8001)
配置中心：
- 实现各个环境的配置，应用程序名称-环境名称.yml
- 数据库及连接池相关配置

### eureka-server(8002)
注册中心服务端：
- 服务注册
- 服务发现

## 服务

### licensing-service(8801)
许可证服务：
- 数据库配置依赖配置中心
- 许可证服务注册至注册中心


### organization-service(8802)
组织服务
- 数据库配置依赖配置中心