package pers.fs.framework.bean;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;


/**
 *
 * 通知、增强处理（Advice）： 就是你想要的功能，你给先定义好，然后在想用的地方用一下，包含Aspect的一段处理代码。<br/>
 * 连接点（JoinPoint）： 就是spring允许你是通知（Advice）的地方，基本每个方法的前、后（两者都有也行），或抛出异常时都可以是连接点，spring只支持方法连接点。其他如AspectJ还可以让你在构造器或属性注入时都行，不过只要记住，和方法有关的前前后后都是连接点。连接点就是为了获取切点方法的相关信息（所在的类、入参、方法等）<br/>
 * 切入点（Pointcut） ：上面说的连接点的基础上，来定义切入点，你的一个类里，有15个方法，那就有十几个连接点了，但是并不想在所有方法附近都使用通知（使用叫织入），只是想让其中几个，在调用这几个方法之前、之后或者抛出异常时干点什么，那么就用切入点来定义这几个方法，让切点来筛选连接点，选中那几个你想要的方法。<br/>
 * 切面（Aspect） ：切面是通知和切入点的结合。连接点就是为了方便理解切点的，明白这个概念就行了。通知说明了干什么和什么时候干（什么时候通过方法名中的befor，after，around等就能知道），切入点说明了在哪干（指定到底是哪个方法），这就是一个完整的切面定义。
 *
 * @author FS
 */
@Aspect
public class InjectValueAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(InjectValueAspect.class);

    @Before("@within(org.springframework.stereotype.Controller) || @annotation(org.springframework.stereotype.Controller)")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        HttpServletRequest request = null;
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            request = requestAttributes.getRequest();
        }
        String token = request.getHeader("token");

        Object[] args = joinPoint.getArgs();


        return joinPoint.proceed();
    }
}
