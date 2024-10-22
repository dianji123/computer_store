package com.hzh.computer_store.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect //将当前类标记为切面类
@Component //将当前类的对象创建使用维护交由Spring容器维护
public class TimerAspect {

    /**
     * AOP 面向切面（Aspect）编程
     * 对业务某一些方法同时添加相同的功能需求,并且在不改变业务功能逻辑的基础之上进行完成,就可以使用AOP的切面编程进行开发
     */

    /**
     * 使用步骤:
     * 首先定义一个类,将这个类作为切面类
     * 在这个类中定义切面方法(5种:前置,后置,环绕,异常,最终)
     * 将这个切面方法中的业务逻辑对应的代码进行编写和设计
     * 通过连接点来连接目标方法,就是用粗粒度表达式和细粒度表达式来进行连接
     */

    /**
     * 切面方法
     * 1.切面方法的访问权限是public。
     * 2.切面方法的返回值类型可以是void或Object，如果该方法被@Around注解修饰，必须使用Object作为返回值类型，并返回连接点方法的返回值；如果使用的注解是@Before或@After等其他注解时，则自行决定。
     * 3.切面方法的名称可以自定义。
     * 4.切面方法可以接收参数,参数是ProccedingJoinPoint接口类型的参数.但是@Around所修饰的方法必须要传递这个参数.其他注解修饰的方法要不要该参数都可以
     */

    /**
     * 第一个*表示方法返回值是任意的
     * 第二个*表示imp包下的类是任意的
     * 第三个*表示类里面的方法是任意的
     * (…)表示方法的参数是任意的
     */
    @Around("execution(* com.hzh.computer_store.service.impl.*.*(..))")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        //开始时间
        long start = System.currentTimeMillis();
        //调用目标方法
        Object result = pjp.proceed();
        //结束时间
        long end = System.currentTimeMillis();
        System.out.println("耗时: " + (end-start));
        return result;
    }

}
