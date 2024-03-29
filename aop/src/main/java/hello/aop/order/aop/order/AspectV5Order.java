package hello.aop.order.aop.order;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;

@Slf4j
public class AspectV5Order {

    @Aspect
    @Order(2)
    public static class LogAspect {
        // 외부 pointcut 참조시 package 명시
        @Around("hello.aop.order.aop.order.Pointcuts.allOrder()")
        public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[log] {}", joinPoint.getSignature());
            return joinPoint.proceed(); // call target
        }
    }


    @Aspect
    @Order(1)
    public static class TxAspect {
        // hello.aop.order 패키지와 하위 패키지이면서 클래스 이름 패턴이 *Service
        @Around("hello.aop.order.aop.order.Pointcuts.orderAndService()")
        public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
            try {
                log.info("[Transaction Start] {}", joinPoint.getSignature());
                Object result = joinPoint.proceed();
                log.info("[Transaction Commit] {}", joinPoint.getSignature());
                return result;
            } catch (Exception e) {
                log.info("[Transaction Rollback] {}", joinPoint.getSignature());
                throw e;
            } finally {
                log.info("[Resource release] {}", joinPoint.getSignature());
            }
        }
    }
}
