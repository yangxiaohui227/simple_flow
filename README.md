<<<<<<< HEAD
# simple_flow
Simple, lightweight rule engine framework
=======
# Simple process engine framework

### Introduce:

 This is a very lightweight, simple, and high-performance process engine framework

### Enviroment:

​    jdk1.8 + above     spring5.0.5+above

### Branch:

  The main branch is No longer maintained

### Quick start:

- Add dependency to your project

  ```xml
      <dependency>
          <groupId>io.github.yxh-flow</groupId>
          <artifactId>flow_core</artifactId>
          <version>1.0.0-RELEASE</version>
      </dependency>
  ```

- Create your business Invoker that extend simple_flow framework Invoker Interface,such as :

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

- create simple_flow.xml which used for process planning  then place this file in folder resources, 'a' is the beanName of A class

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

- Add @EnableSimpleFlow annotation to the A startup class 

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

- executive process

  ![chain](.\chain.png)

### More detail:

Please refer to the project demo:  springboot_flow_demo

### Suggest:

If you have any good ideas, please contact me at: 15902048215@163.com

​     
>>>>>>> 0ed93865825b950f98a31e8c0f43cfec19a1c368
