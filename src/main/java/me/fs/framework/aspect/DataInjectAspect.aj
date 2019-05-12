package me.fs.framework.aspect;

import me.fs.framework.annotation.ReturnEnhancer;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;
import java.util.Collection;

@Aspect
public class DataInjectAspect {
    @Pointcut("@annotation(me.fs.framework.annotation.ReturnEnhancer)")
    public void targetMethod() {
    }

    /**
     *
     * 也可换一种写法:@AfterReturning(pointcut = "@annotation(me.fs.framework.annotation.DataInjectAspect)")
     * @param joinPoint
     * @param result
     */
    @AfterReturning(value = "targetMethod()", returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result) throws NoSuchMethodException {
        if (result != null) {
            Object target = joinPoint.getTarget();

            Signature signature = joinPoint.getSignature();
            if (!(signature instanceof MethodSignature)) {
                // 切入的位置还可以不是方法吗？
                return;
            }

            MethodSignature methodSignature = (MethodSignature) signature;
            // 当前执行的方法
            Method method = target.getClass().getMethod(methodSignature.getName(), methodSignature.getParameterTypes());
            // 当前方法上的注解
            ReturnEnhancer annotation = method.getAnnotation(ReturnEnhancer.class);

            // 关联字段
            // 映射字段

            if (result instanceof Collection) {
                Collection collection = (Collection) result;
                for (Object e : collection) {

                }
            } else {
                Object e = result;


            }
        }
    }

    public static abstract class DataInjectAdvice {
        public abstract  <T> void inject(T t);
    }
}
