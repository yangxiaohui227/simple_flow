# Simple process engine framework/最轻量的流程引擎

### Introduce/介绍:

This is a very lightweight, simple, and high-performance process engine framework

这是一个最轻量，最简单的，高效的流程引擎框架

### Enviroment/环境:

​    jdk1.8 + above     spring5.0.5+above

​    jdk版本最低1.8以上，spring 5.05版本或者以上

### Branch/分支:

The main branch is No longer maintained

main分支不再维护

### Quick start/快速开始:

- Add dependency to your project /在项目中引入框架核心依赖

  ```xml
      <dependency>
          <groupId>io.github.yxh-flow</groupId>
          <artifactId>flow_core</artifactId>
          <version>1.0.0-RELEASE</version>
      </dependency>
  ```

- Create your business Invoker that extend simple_flow framework Invoker Interface,such as :

  创建你自己的业务Invoker并继承框架的Invoker接口
  
  ```java
  @Service
  public class A extends Invoker {
      @Override
      public void invoker(InvokerContextRequest A) {
          System.out.println("this is A and current threadId is"+Thread.currentThread().getId());
      }
  }
  ```

​       In this example,I will create some Invoker which named A B C D E

​       在这个例子中，我将会创建A B C D E这几个Invoker执行器

- create simple_flow.xml which used for process planning  then place this file in folder resources, 'a' is the beanName of A class

  创建一个flow.xml放到项目resources文件夹下，其中a代表的是A这个对象在spring容器中的BeanName

```xml
<?xml version="1.0" encoding="UTF-8"?>
<flow>
  <chain name="chain1">
    <invoker>a</invoker>
    <batch>
      <invoker>c</invoker>
      <invoker>d</invoker>
    </batch>
    <invoker>b</invoker>
  </chain>
  <chain name="chain2">
    <batch>
      <invoker>b</invoker>
    </batch>

    <batch>
      <invoker>c</invoker>
      <invoker>d</invoker>
    </batch>
  </chain>
</flow>
```

<invoker> Represents serial execution     <chain> Represents parallel execution

<invoker>这个标签代表串行执行                 <chain> 这个标签代表并行执行

- Add @EnableSimpleFlow annotation to the A startup class

  在主启动类中添加注解 @EnableSimpleFlow

  ```java
  @SpringBootApplication
  @EnableSimpleFlow
  public class FlowServerApplication {
      public static void main(String[] args) {
          SpringApplication.run(FlowServerApplication.class, args);
      }
      
  }
  ```

- Last,you can create a controller to test this case; example,I select chain1 to test

  最后，创建一个测试controller,在这个例子中，我用chain1这个流程来测试

```java
@RestController
public class TestController {

  @Autowired
  private ThreadPoolTaskExecutor shopTaskExecutor;

  @RequestMapping("/test")
  public String doInvoker(){
    InvokerContextRequest invokerContextRequest = new InvokerContextRequest();
    invokerContextRequest.setParam("this is test ....");
    FlowInvokerChainUtil.getInvokerHolder("chain1").doInvoker(shopTaskExecutor,invokerContextRequest);
    System.out.println("total cost:"+invokerContextRequest.getCostTime());
    return "ok";
  }


}
```

You can specify different Thread Pool for each chain; Achieve the effect of resource isolation

你可以对不同的流程使用不同的线程池进行隔离

- executive process：/执行过程图，原理是基于责任链模式

  ![chain](.\chain.png)

### More detail:/更多细节

Please refer to the project demo:  springboot_flow_demo

想了解更多细节，可以下载源码，在 springboot_flow_demo这个模块，有测试demo

### Suggest: /建议

If you have any good ideas, please contact me at: 15902048215@163.com

如果有更好的建议，请给我的邮箱发信息

​     