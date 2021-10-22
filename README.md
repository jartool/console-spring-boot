# console-spring-boot
### 简介

------------

	基于websocket和驱逐队列写的一个小工具,可以浏览器实时查看控制台日志。
### maven依赖

------------

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-websocket</artifactId>
</dependency>
```
### logback-spring.xml配置

------------
	ConsoleAppender Add: <filter class="io.github.jartool.console.filter.LogFilter"></filter>
```xml
<appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
    <filter class="ch.qos.logback.classic.filter.ThresholdFilter">
        <level>INFO</level>
    </filter>
    <filter class="io.github.jartool.console.filter.LogFilter"></filter>
    <encoder>
        <Pattern>${CONSOLE_LOG_PATTERN}</Pattern>
        <charset>UTF-8</charset>
    </encoder>
</appender>
```
### Springboot配置

------------
##### 启动类
	启动类Add: @EnableConsole注解即可
```java
@EnableConsole
@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
```
##### yml
```yaml
jartool:
  console:
    view: /console  #控制台日志访问地址,默认: /console
    queue:
      size: 1000  #日志队列容量,默认: 1000
    auth:
      enable: true  #是否开启页面授权,默认: true
      url: /consoleAuth  #授权校验接口url,默认: /consoleAuth
      key: auth  #授权key,默认: auth
      username: admin  #用户名,默认: admin
      password: admin  #密码,默认: admin
```
