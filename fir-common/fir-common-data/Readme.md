# 问题描述

## 1.org.apache.ibatis.binding.BindingException: Invalid bound statement (not found):

> 该问题出现是找不动对应的xml文件

解决方案如下：

1. mapper.xml没有按照传统的maven架构进行放置
   
   > 如果我们的mapper.xml文件没有放置到src-main-resources下面，是不会被maven build plugin给默认扫描到的。此时需要修改启动的模块的pom文件，在build标签里面加入：
   
   ```java
   <build>
   <resources>
       <resource>
           <directory>src/main/java</directory>
           <includes>
               <include>**/*.xml</include>
               <include>**/*.properties</include>
           </includes>
           <filtering>true</filtering>
       </resource>
       <resource>
           <directory>src/main/resources</directory>
           <includes>
               <include>**/*.xml</include>
               <include>**/*.properties</include>
           </includes>
           <filtering>true</filtering>
       </resource>
   </resources>
   </build>
   ```

2. mybatis的配置信息出错
   
   ```properties
           mybatis-plus.mapper-locations=classpath*:/mapper/**Mapper.xml
   ```

3. idea的编译问题

> 点击导航栏build下面的rebuild project

如果以上方法都不行使用以下方法：

> 如果我们把 Mapper 接口放在 src/main/java 下的 cn.zzs.mybatis.mapper，那么 Mapper xml 文件就应该放在 src\main\resources 下的 cn\zzs\mybatis\mapper。

[Mybatis源码详解系列(一)--持久层框架解决了什么及如何使用Mybatis - 子月生 - 博客园 (cnblogs.com)](https://www.cnblogs.com/ZhangZiSheng001/p/12603885.html)
