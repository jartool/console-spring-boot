# console-spring-boot

### 一、简介

------------

	基于websocket和驱逐队列写的一个小工具,可以浏览器实时查看控制台日志。
![](https://s3.bmp.ovh/imgs/2021/10/a1513e32a6467d22.png)
### 二、食用方式
- #### maven

------------

```xml
<!-- console-spring-boot -->
<dependency>
    <groupId>io.github.jartool</groupId>
    <artifactId>console-spring-boot</artifactId>
    <version>1.0.2</version>
</dependency>
<!-- thymeleaf -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
<!-- websocket -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-websocket</artifactId>
</dependency>
```
- #### logback-spring.xml

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
- #### configuration

------------
```java
//启动类Add: @EnableConsole注解即可
@EnableConsole
@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
```
```yaml
#yml配置
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
